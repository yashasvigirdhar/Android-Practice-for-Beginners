package org.openintents.filemanager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Dropbox extends Activity implements OnNavigationListener,
		OnClickListener {

	private DbxAccountManager mDbxAcctMgr;
	private DbxFileSystem dbxFs;
	private String appKey = "ijcxja4c41evhxt";
	private String appSecret = "rre5pkobmp7ki7v";
	private static final int REQUEST_LINK_TO_DBX = 5;
	

	// xml linkings
	Button movehere , delete;
	TextView mTestOutput;
	EditText namedelfile;

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
		getActionBar().setSelectedNavigationItem(1);
		getActionBar().show();

		// dropbox
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(),
				appKey, appSecret);
		initialize();
		checkdropconnect();

	}

	private void showallfiles() {
		// TODO Auto-generated method stub
		try {
			
			Toast.makeText(this, "pressed", Toast.LENGTH_LONG).show();
			Log.d("button show pressed", "button show pressed");
			// Print the contents of the root folder. This will block until
			// we can
			// sync metadata the first time.
			List<DbxFileInfo> infos = dbxFs.listFolder(DbxPath.ROOT);
			mTestOutput.setText("your drop box folder contains");
			for (DbxFileInfo info : infos) {
				mTestOutput.append("    " + info.path + ", last modified:"
						+ info.modifiedTime + '\n');
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void initialize() {
		// TODO Auto-generated method stub
		movehere = (Button) findViewById(R.id.bmovehere);
		mTestOutput = (TextView)findViewById(R.id.mTestOutput);
		movehere.setOnClickListener(this);
		namedelfile = (EditText)findViewById(R.id.etdelfile);
		delete = (Button)findViewById(R.id.bdelfile);
		delete.setOnClickListener(this);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// TODO Auto-generated method stub
		switch (position){
		case 0:
			finish();
			break;
		case 1:
			return false;
		case 2:
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()){
		case R.id.bmovehere:
			addfiletodropbox();
			break;
		case R.id.bdelfile:
			deletefile();
			break;
		}
	}

	private void deletefile() {
		// TODO Auto-generated method stub
		final String TEST_FILE_NAME2 = namedelfile.getText().toString();
		DbxPath testPath2 = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME2);
		Toast.makeText(this, "file name " + TEST_FILE_NAME2 + " path " + testPath2,
				Toast.LENGTH_LONG).show();
		try {
			if (dbxFs.exists(testPath2)) {
				dbxFs.delete(testPath2);

				Toast.makeText(this,"\ndeleted file '" + testPath2 + "'.\n",Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this,"\nfile doesn't exists with '"
						+ testPath2 + "'.\n",Toast.LENGTH_LONG).show();
			}

		} catch (DbxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		showallfiles();
	}

	private void addfiletodropbox() {
		// TODO Auto-generated method stub
		File myfile = FileManagerActivity.mContextFile;
		String dummy[] = myfile.toString().split("/");
		// Log.d("file name", "file name");
		final String FILE_NAME = dummy[dummy.length - 1];
		DbxPath testPath = new DbxPath(DbxPath.ROOT, FILE_NAME);
		//Dbxf
		Toast.makeText(this, "file name " + FILE_NAME + " path " + testPath,
				Toast.LENGTH_LONG).show();
		try {
			if (!dbxFs.exists(testPath)) {
				if (myfile.isFile()) {
					DbxFile testFile = dbxFs.create(testPath);
					testFile.writeFromExistingFile(myfile, false);
				}
				else{
					dbxFs.createFolder(testPath);
				}
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
	
		showallfiles();
	}

	private void checkdropconnect() {
		// TODO Auto-generated method stub
		if (mDbxAcctMgr.hasLinkedAccount()) {
			try {
				dbxFs = DbxFileSystem
						.forAccount(mDbxAcctMgr.getLinkedAccount());
				showallfiles();
			} catch (Unauthorized e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr
							.getLinkedAccount());
					showallfiles();
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// mTestOutput.setText("Link to Dropbox failed or was cancelled.");
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
