/**
 * 
 */
package itjava.tests;

import itjava.model.TutorialInfo;
import itjava.model.TutorialInfoStore;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Aniket
 *
 */
public class TutorialInfoStoreTest {

	private ArrayList<TutorialInfo> tutorialInfoList;
	private HashMap<String, String> whereClause;

	@Test
	public void TestFullInsertOf1TutorialInTutorialInfo() {
		Given1TutorialInList();
		WhenInsertIsCalled();
		ThenCheckIfTableHasInsertedEntry();
	}
	
	@Test
	public void TestFullInsertInTutorialInfo() {
		Given2TutorialInList();
		WhenInsertIsCalled();
		ThenCheckIfTableHasInsertedEntries();
	}

	private void ThenCheckIfTableHasInsertedEntries() {
		whereClause = new HashMap<String, String>();
		whereClause.put("folderName", "folderName1");
		assertEquals("currentUser1", TutorialInfoStore.SelectInfo(whereClause).get(0).getCreatedBy());
		whereClause.clear();
		whereClause.put("tutorialName", "Tutorial Name2");
		assertEquals("description...", TutorialInfoStore.SelectInfo(whereClause).get(0).getTutorialDescription());
	}

	private void Given2TutorialInList() {
		tutorialInfoList = new ArrayList<TutorialInfo>();
		TutorialInfo tutorialInfo1 = new TutorialInfo("folderName1", "Tutorial Name1", "currentUser1", null, 1, 1);
		TutorialInfo tutorialInfo2 = new TutorialInfo("folderName2", "Tutorial Name2", "currentUser2", "description...", 0, 5);
		tutorialInfoList.add(tutorialInfo1);
		tutorialInfoList.add(tutorialInfo2);
	}

	private void ThenCheckIfTableHasInsertedEntry() {
		whereClause = new HashMap<String, String>();
		whereClause.put("folderName", "folderName");
		assertEquals("currentUser", TutorialInfoStore.SelectInfo(whereClause).get(0).getCreatedBy()); 
	}

	private void WhenInsertIsCalled() {
		TutorialInfoStore.InsertInfoList(tutorialInfoList);
	}

	private void Given1TutorialInList() {
		tutorialInfoList = new ArrayList<TutorialInfo>();
		TutorialInfo tutorialInfo = new TutorialInfo("folderName", "Tutorial Name", "currentUser", null, 1, 1);
		tutorialInfoList.add(tutorialInfo);
	}

}
