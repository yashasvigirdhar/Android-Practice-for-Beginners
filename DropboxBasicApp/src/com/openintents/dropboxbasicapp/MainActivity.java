package com.openintents.dropboxbasicapp;

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
//import com.openintent.sample.dropbox.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String appKey = "ijcxja4c41evhxt";
	private static final String appSecret = "rre5pkobmp7ki7v";
	private File myfile;

	private static final int REQUEST_LINK_TO_DBX = 0;

	private TextView mTestOutput;
	Button mLinkButton, addfile, deletefile, showfiles, copyfile;
	EditText nameaddfile, namedelfile, pathcopyfile;
	private DbxAccountManager mDbxAcctMgr;
	private DbxFileSystem dbxFs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_main);
		 mDbxAcctMgr = DbxAccountManager.getInstance(
					getApplicationContext(), appKey, appSecret);
		 initializevars();
		 

	}

	

	private void initializevars() {
		// TODO Auto-generated method stub
		mTestOutput = (TextView) findViewById(R.id.test_output);
		mLinkButton = (Button) findViewById(R.id.link_button);
		addfile = (Button) findViewById(R.id.baddfile);
		deletefile = (Button) findViewById(R.id.bdelfile);
		showfiles = (Button) findViewById(R.id.bshowfiles);
		copyfile = (Button) findViewById(R.id.bcopyfile);
		nameaddfile = (EditText) findViewById(R.id.etaddfile);
		namedelfile = (EditText) findViewById(R.id.etdelfile);
		pathcopyfile = (EditText) findViewById(R.id.etcopyfile);
		mLinkButton.setOnClickListener(this);
		addfile.setOnClickListener(this);
		deletefile.setOnClickListener(this);
		showfiles.setOnClickListener(this);
		copyfile.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mDbxAcctMgr.hasLinkedAccount()) {
			showLinkedView();
			try {
				dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
			} catch (Unauthorized e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			showUnlinkedView();
		}
		
	}

	private void showLinkedView() {
		mLinkButton.setVisibility(View.GONE);
		mTestOutput.setVisibility(View.VISIBLE);
	}

	private void showUnlinkedView() {
		mLinkButton.setVisibility(View.VISIBLE);
		mTestOutput.setVisibility(View.GONE);
	}

	private void onClickLinkToDropbox() {
		mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LINK_TO_DBX) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			} else {
				mTestOutput.setText("Link to Dropbox failed or was cancelled.");
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.baddfile:
			final String TEST_DATA = "Hello Dropbox";
			final String TEST_FILE_NAME = nameaddfile.getText().toString();
			DbxPath testPath = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME);

			try {
				if (!dbxFs.exists(testPath)) {
					DbxFile testFile = dbxFs.create(testPath);
					try {
						testFile.writeString(TEST_DATA);
					} finally {
						testFile.close();
					}
					mTestOutput.append("\nCreated new file '" + testPath
							+ "'.\n");
				} else {
					mTestOutput.append("\nfile already exists with '"
							+ testPath + "'.\n");
				}

			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;

		case R.id.bdelfile:
			final String TEST_FILE_NAME2 = namedelfile.getText().toString();
			DbxPath testPath2 = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME2);

			try {
				if (dbxFs.exists(testPath2)) {
					dbxFs.delete(testPath2);

					mTestOutput.append("\ndeleted file '" + testPath2 + "'.\n");
				} else {
					mTestOutput.append("\nfile doesn't exists with '"
							+ testPath2 + "'.\n");
				}

			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case R.id.bshowfiles:
			try {
				Toast.makeText(this, "pressed", Toast.LENGTH_LONG).show();
				Log.d("button show pressed", "button show pressed");
				// Print the contents of the root folder. This will block until
				// we can
				// sync metadata the first time.
				List<DbxFileInfo> infos = dbxFs.listFolder(DbxPath.ROOT);
				mTestOutput.append("\nContents of app folder:\n");
				for (DbxFileInfo info : infos) {
					mTestOutput.append("    " + info.path + ", "
							+ info.modifiedTime + '\n');
				}
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;

		case R.id.bcopyfile:
			myfile = new File(pathcopyfile.getText().toString());
			// System.out.println(myfile.toString());
			String dummy[] = myfile.toString().split("/");
			// Log.d("file name", "file name");
			final String TEST_FILE_NAME3 = dummy[dummy.length - 1];
			// System.out.println(TEST_FILE_NAME3);
			// Toast.makeText(this,
			// Arrays.toString(dummy)+"    "+TEST_FILE_NAME3,
			// Toast.LENGTH_LONG).show();
			DbxPath testPath3 = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME3);
			try {
				DbxFile testFile3 = dbxFs.create(testPath3);
				testFile3.writeFromExistingFile(myfile, false);
			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case R.id.link_button:
			onClickLinkToDropbox();
			break;
		}
	}
}
