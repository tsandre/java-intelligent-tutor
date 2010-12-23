/**
 * 
 */
package itjava.model;

/**
 * @author Aniket
 * Information related to tutorials as stored in the database table named "TutorialInfo"
 */
public class TutorialInfo {
	
	public TutorialInfo() {
		this.folderName = new String();
		this.tutorialName = new String();
		this.createdBy = new String();
		this.tutorialDescription = new String();
		this.numExamples = 0;
		this.numQuizes = 0;
		this.creationDate = new String();
		this.timesAccessed = 0;
	}
	
	public TutorialInfo(String folderName, String tutorialName,
			String createdBy, String tutorialDescription, int numExamples,
			int numQuizes) {
		this.folderName = folderName;
		this.tutorialName = tutorialName;
		this.createdBy = createdBy;
		this.tutorialDescription = tutorialDescription;
		this.numExamples = numExamples;
		this.numQuizes = numQuizes;
	}
	
	public TutorialInfo(int tutorialId, String folderName,
			String tutorialName, String tutorialDescription,
			int numExamples, int numQuizes, String creationDate,
			String createdBy, int timesAccessed) {
		
		this.tutorialId = tutorialId;
		this.folderName = folderName;
		this.tutorialName = tutorialName;
		this.createdBy = createdBy;
		this.tutorialDescription = tutorialDescription;
		this.numExamples = numExamples;
		this.numQuizes = numQuizes;
		this.creationDate = creationDate;
		this.timesAccessed = timesAccessed;
	}

	private int tutorialId;
	private String folderName;
	private String tutorialName;
	private String createdBy;
	private String tutorialDescription;
	private int numExamples;
	private int numQuizes;
	private String creationDate;
	private int timesAccessed;
	
	/**
	 * @return the tutorialId
	 */
	public int getTutorialId() {
		return tutorialId;
	}
	
	/**
	 * @param tutorialId the tutorialId to set
	 */
	public void setTutorialId(int tutorialId) {
		this.tutorialId = tutorialId;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}
	
	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the tutorialName
	 */
	public String getTutorialName() {
		return tutorialName;
	}
	/**
	 * @param tutorialName the tutorialName to set
	 */
	public void setTutorialName(String tutorialName) {
		this.tutorialName = tutorialName;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the tutorialDescription
	 */
	public String getTutorialDescription() {
		return tutorialDescription;
	}
	/**
	 * @param tutorialDescription the tutorialDescription to set
	 */
	public void setTutorialDescription(String tutorialDescription) {
		this.tutorialDescription = tutorialDescription;
	}
	/**
	 * @return the numExamples
	 */
	public int getNumExamples() {
		return numExamples;
	}

	/**
	 * @param numExamples the numExamples to set
	 */
	public void setNumExamples(int numExamples) {
		this.numExamples = numExamples;
	}

	/**
	 * @return the numQuizes
	 */
	public int getNumQuizes() {
		return numQuizes;
	}

	/**
	 * @param numQuizes the numQuizes to set
	 */
	public void setNumQuizes(int numQuizes) {
		this.numQuizes = numQuizes;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @return the timesAccessed
	 */
	public int getTimesAccessed() {
		return timesAccessed;
	}

}
