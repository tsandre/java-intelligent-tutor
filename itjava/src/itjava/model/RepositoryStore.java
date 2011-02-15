/**
 * 
 */
package itjava.model;

import itjava.data.LocalMachine;
import itjava.data.NodeToCompare;
import itjava.db.DBConnection;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @author Aniket
 * 
 */
public class RepositoryStore {

	private Repository _repository;
	private ArrayList<String> _tempVariableDeclarations;
	private ArrayList<String> _tempMethodInvocations;
	private ArrayList<String> _tempClassInstances;
	private Connection _conn;
	
	private RepositoryStore() {
		_repository = new Repository();
		_tempVariableDeclarations = new ArrayList<String>();
		_tempMethodInvocations = new ArrayList<String>();
		_tempClassInstances = new ArrayList<String>();
		_conn = null;
	}

	/**
	 * Seeks original {@link Repository} by performing necessary I/O and updates
	 * it with the argument passed, i.e. {@link ArrayList}<
	 * {@link CompilationUnitFacade}>. This method will also update the
	 * {@link Repository} object with count of terms present in the
	 * {@link ArrayList}
	 * 
	 * @param compilationUnitFacadeList
	 * @return updated {@link Repository}
	 */
	public static Repository UpdateRepository(
			ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {

		RepositoryStore _rStore = new RepositoryStore();
		boolean repositoryUpdated = false;
		_rStore._repository = _rStore.ReadRepository();
		if (compilationUnitFacadeList != null) {
			for (CompilationUnitFacade facade : compilationUnitFacadeList) {
				if (!_rStore._repository.Contains(facade.getUrl())) {
					repositoryUpdated = true;
					_rStore.SaveFile(facade);
					_rStore.InitRepositoryStore();
					_rStore.UpdateImportTerms(facade.getImportDeclarations());
					_rStore.UpdateVariableDeclarationTerms(facade
							.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT));
					_rStore.UpdateClassInstanceTerms(facade.getClassInstances());
					_rStore.UpdateMethodInvocationTerms(facade.getMethodInvocations());
					_rStore._repository.allDocuments.add(facade);
					_rStore._repository.allUrls.add(facade.getUrl());
				}
			}
			if (repositoryUpdated) {
				_rStore.WriteRepository();
			}
		}
		return _rStore._repository;
	}

	private void SaveFile(CompilationUnitFacade facade) {
		int fileName = 0;
		/*Writing file name & URL to DB*/
		try {
			GetConnection();
			PreparedStatement insertStmt = _conn.prepareStatement("insert into Documents(url) values(?);");
			insertStmt.setString(1, facade.getUrl());
			int rowsInserted = insertStmt.executeUpdate();
			System.out.println("Num of rows inserted in table Documents: " + rowsInserted);
			PreparedStatement fileNameSql = _conn.prepareStatement("select fileName from Documents where url = ?");
			fileNameSql.setString(1, facade.getUrl());
			ResultSet rs = fileNameSql.executeQuery();
			while (rs.next()) {
				System.out.println("FileName is : " + rs.getInt("fileName"));
				fileName = rs.getInt("fileName");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(_conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/*Writing the actual file to disk*/
		FileWriter writer = null;
		try{ 
			writer = new FileWriter(LocalMachine.home + "samples/" + fileName + ".sample");
/*			for (String line : facade.getLinesOfCode()) {
				writer.append(line + "\n");
			}*/
			writer.write(facade.getUnformattedSource());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reads original {@link Repository} saved in the system by performing I/O
	 * <p>
	 * {@code TODO Implement File IO or Database IO}
	 */
	public Repository ReadRepository() {
		Repository repository = new Repository();
		try {
			GetConnection();
			repository.importTerms = GetTermFromDB("ImportTerms");
			repository.superTypeTerms = GetTermFromDB("SuperTypeTerms");
			repository.variableDeclarationTerms = GetTermFromDB("VariableDeclarationTerms");
			repository.classInstanceTerms = GetTermFromDB("ClassInstanceTerms");
			repository.methodInvocationTerms = GetTermFromDB("MethodInvocationTerms");
			repository.propertyAssignmentTerms = GetTermFromDB("PropertyAssignmentTerms");
	
			CompilationUnitStore cuStore = new CompilationUnitStore();
			HashMap<String, String> fileContentsToUrlMap = new HashMap<String, String>();
			ArrayList<String> allUrls = new ArrayList<String>();
			for (Entry<String, String> entrySet : GetDocumentsFromDB().entrySet()) {
				try{
					fileContentsToUrlMap.put(Convertor.FileToString(entrySet.getKey()), entrySet.getValue());
					allUrls.add(entrySet.getValue());
				} catch (Exception e) {
					System.err.println("Error opening file : " + entrySet.getKey());
					System.err.println("URL not added to rep : " + entrySet.getValue());
					e.printStackTrace();
				}
			}
			ArrayList<ResultEntry> resultEntryList = ResultEntryStore.createResultEntryList(fileContentsToUrlMap);
			System.out.println("Now creating compilation units of snippets saved in the repository..");
			repository.allDocuments = cuStore.createCompilationUnitFacadeList(resultEntryList);
			repository.allUrls = allUrls;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(this._conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return repository;
	}

	private TreeMap<String, Integer> GetTermFromDB(String termType) {
		TreeMap<String, Integer> allTuples = new TreeMap<String, Integer>();
		try {
			java.sql.Statement sqlStatement = _conn.createStatement();
			ResultSet rs = sqlStatement.executeQuery("select * from " + termType + ";");
			while (rs.next()) {
				allTuples.put(rs.getString("term"), rs.getInt("numOccurrences"));
			}
				rs.close();
		} catch (Exception s) {
			s.printStackTrace();
		}
		return allTuples;
	}

	private void WriteTermsToDB(String tableName) {
		Set<Entry<String, Integer>> entrySet = null;
		if (tableName.equals("ImportTerms")) {
			entrySet = _repository.importTerms.entrySet();
		} else if (tableName.equals("SuperTypeTerms")) {
			entrySet = _repository.superTypeTerms.entrySet();
		} else if (tableName.equals("VariableDeclarationTerms")) {
			entrySet = _repository.variableDeclarationTerms.entrySet();
		} else if (tableName.equals("ClassInstanceTerms")) {
			entrySet = _repository.classInstanceTerms.entrySet();
		} else if (tableName.equals("MethodInvocationTerms")) {
			entrySet = _repository.methodInvocationTerms.entrySet();
		} else if (tableName.equals("PropertyAssignmentTerms")) {
			entrySet = _repository.propertyAssignmentTerms.entrySet();
		}
		try {
			PreparedStatement prep = _conn.prepareStatement("replace into "
					+ tableName + " (term, numOccurrences) values(?,?)");
			for (Entry<String, Integer> entry : entrySet) {
				prep.setString(1, entry.getKey());
				prep.setInt(2, entry.getValue());
				prep.addBatch();
			}
			prep.executeBatch();
		} catch (Exception e) {
			System.err.println("Error in writing to database: Table name: " + tableName);
			e.printStackTrace();
		}
	}

	private HashMap<String, String> GetDocumentsFromDB() {
		HashMap<String, String> allTuples = new HashMap<String, String>();
		try {
			java.sql.Statement sqlStatement = _conn.createStatement();
			ResultSet rs = sqlStatement.executeQuery("select * from Documents;");
			while (rs.next()) {
				allTuples.put(LocalMachine.home + "samples/" + rs.getInt("fileName") + ".sample",
						rs.getString("url"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTuples;
	}

	/**
	 * Saves the passed {@link Repository} into the system by necessary I/O
	 * operations.
	 * 
	 * @param repository
	 */
	public void WriteRepository() {
		try {
			GetConnection();
			WriteTermsToDB("ImportTerms");
			WriteTermsToDB("SuperTypeTerms");
			WriteTermsToDB("VariableDeclarationTerms");
			WriteTermsToDB("ClassInstanceTerms");
			WriteTermsToDB("MethodInvocationTerms");
			WriteTermsToDB("PropertyAssignmentTerms");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(_conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void InitRepositoryStore() {
		_tempVariableDeclarations = new ArrayList<String>();
		_tempMethodInvocations = new ArrayList<String>();
		_tempClassInstances = new ArrayList<String>();
	}

	private void UpdateImportTerms(
			List<ImportDeclaration> importDeclarations) {
		for (ImportDeclaration importDeclaration : importDeclarations) {
			String importTerm = importDeclaration.getName()
					.getFullyQualifiedName();
			int numOfDocsContainingTerm = 1;
			if (_repository.importTerms.containsKey(importTerm)) {
				numOfDocsContainingTerm += _repository.importTerms
						.get(importTerm);
			}
			_repository.importTerms.put(importTerm, numOfDocsContainingTerm);
		}
	}

	private void UpdateVariableDeclarationTerms(
			List<Statement> statements) {
		for (Statement statement : statements) {
			VariableDeclarationStatement variableDeclarationStatement = (VariableDeclarationStatement) statement;
			String variableTerm = variableDeclarationStatement.getType()
					.toString();
			if (!_tempVariableDeclarations.contains(variableTerm)) {
				_tempVariableDeclarations.add(variableTerm);
				int numOfDocsContainingTerm = 1;
				if (_repository.variableDeclarationTerms
						.containsKey(variableTerm)) {
					numOfDocsContainingTerm += _repository.variableDeclarationTerms
							.get(variableTerm);
				}
				_repository.variableDeclarationTerms.put(variableTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private void UpdateMethodInvocationTerms(
			List<SimpleName> methodInvocations) {
		for (SimpleName methodInvocation : methodInvocations) {
			String methodInvocationTerm = methodInvocation.toString();
			if (!_tempMethodInvocations.contains(methodInvocationTerm)) {
				_tempMethodInvocations.add(methodInvocationTerm);
				int numOfDocsContainingTerm = 1;
				if (_repository.methodInvocationTerms
						.containsKey(methodInvocationTerm)) {
					numOfDocsContainingTerm += _repository.methodInvocationTerms
							.get(methodInvocationTerm);
				}
				_repository.methodInvocationTerms.put(methodInvocationTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private void UpdateClassInstanceTerms(List<Type> classInstances) {
		for (Type classInstance : classInstances) {
			String classInstanceTerm = classInstance.toString();
			if (!_tempClassInstances.contains(classInstanceTerm)) {
				_tempClassInstances.add(classInstanceTerm);
				int numOfDocsContainingTerm = 1;
				if (_repository.classInstanceTerms
						.containsKey(classInstanceTerm)) {
					numOfDocsContainingTerm += _repository.classInstanceTerms
							.get(classInstanceTerm);
				}
				_repository.classInstanceTerms.put(classInstanceTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private void GetConnection() throws Exception {
			_conn = DBConnection.GetConnection();
	}
}
