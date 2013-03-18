package org.openintents.filemanager;

import java.io.File;
import java.io.IOException;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class Dropbox extends Activity implements OnNavigationListener,
		OnClickListener {

	private DbxAccountManager mDbxAcctMgr;
	private DbxFileSystem dbxFs;
	private String appKey = "ijcxja4c41evhxt";
	private String appSecret = "rre5pkobmp7ki7v";
	private static final int REQUEST_LINK_TO_DBX = 5;

	// xml linkings
	Button movehere;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Context context = getActionBar().getThemedContext();
		setContentView(R.layout.activity_dropbox);

		// action bar thing
		getActionBar().setListNavigationCallbacks(
				new ArrayAdapter<String>(context,
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_local),
								getString(R.string.title_dropbox),
								getString(R.string.title_gdrive) }),
				Dropbox.this);

		
		getActionBar().setDisplayUseLogoEnabled(true);
		// getActionBar().setSubtitle("Enter Amount");
		getActionBar().setNavigationMode(getActionBar().NAVIGATION_MODE_LIST);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setTitle("OI File Manager");
		getActionBar().setSelectedNavigationItem(2);
		getActionBar().show();

		// dropbox
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(),
				appKey, appSecret);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		movehere = (Button) findViewById(R.id.bmovehere);
		movehere.setOnClickListener(this);
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		checkdropconnect();
		
		
	}

	private void addfiletodropbox() {
		// TODO Auto-generated method stub
		File myfile = FileManagerActivity.mContextFile;
		String dummy[] = myfile.toString().split("/");
		// Log.d("file name", "file name");
		final String FILE_NAME = dummy[dummy.length - 1];
		DbxPath testPath = new DbxPath(DbxPath.ROOT, FILE_NAME);
		Toast.makeText(this, "file name " + FILE_NAME + " path " + testPath,
				Toast.LENGTH_LONG).show();
		try {
			if (!dbxFs.exists(testPath)) {
				DbxFile testFile = dbxFs.create(testPath);
				testFile.writeFromExistingFile(myfile, false);
			} else {
				Toast.makeText(
						this,
						"\nfile already exists with name'" + FILE_NAME + "'.\n",
						Toast.LENGTH_LONG).show();
			}
		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void checkdropconnect() {
		// TODO Auto-generated method stub
		if (mDbxAcctMgr.hasLinkedAccount()) {	
			try {
				dbxFs = DbxFileSystem
						.forAccount(mDbxAcctMgr.getLinkedAccount());
			} catch (Unauthorized e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
					addfiletodropbox();
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			} else {
				//mTestOutput.setText("Link to Dropbox failed or was cancelled.");
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
