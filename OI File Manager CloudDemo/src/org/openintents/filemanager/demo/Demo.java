/* 
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openintents.filemanager.demo;

import java.io.File;
import java.io.IOException;

import org.openintents.intents.FileManagerIntents;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author yash
 *
 */
/**
 * @author yash
 * 
 */
public class Demo extends Activity {

	protected static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
	protected static final int REQUEST_CODE_GET_CONTENT = 2;
	private static final int REQUEST_CODE_GET_FILE_FOR_DROP = 3;
	// private static final int REQUEST_CODE_DEL_FILE_FOR_DROP
	private static final int REQUEST_LINK_TO_DBX = 5;

	protected EditText mEditText;
	protected TextView mTextView;

	private static boolean delete = false;

	/**
	 * dropbox variables
	 */

	private DbxAccountManager mDbxAcctMgr;
	private DbxFileSystem dbxFs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mEditText = (EditText) findViewById(R.id.file_path);
		mTextView = (TextView) findViewById(R.id.info);

		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(),
				Dropboxconfig.appKey, Dropboxconfig.appSecret);
	}

	public void onClickOpenFile(View view) {
		openFile();
	}

	public void onClickSaveFile(View view) {
		saveFile();
	}

	public void onClickPickDirectory(View view) {
		pickDirectory();
	}

	public void onClickGetContent(View view) {
		getContent();
	}

	public void onClickOpenUri(View view) {
		openFileUri();
	}

	/**
	 * Opens the file manager to select a file to open.
	 */
	public void openFile() {
		String fileName = "";

		Log.d("fileName", fileName);
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE,
				getString(R.string.open_title));// select file to open
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT,
				getString(R.string.open_button));// open

		try {
			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Opens the file manager to select a location for saving a file.
	 */
	private void saveFile() {
		String fileName = "";

		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE,
				getString(R.string.save_title));
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT,
				getString(R.string.save_button));

		try {
			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Opens the file manager to pick a directory.
	 */
	private void pickDirectory() {
		String fileName = "";

		// Note the different intent: PICK_DIRECTORY
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_DIRECTORY);

		// Construct URI from file name.
		File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE,
				getString(R.string.pick_directory_title));
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT,
				getString(R.string.pick_directory_button));

		try {
			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Use GET_CONTENT to open a file.
	 */
	public void getContent() {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(intent, REQUEST_CODE_GET_CONTENT);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Use URI to get a file
	 */
	public void openFileUri() {
		String filePath = "";
		Uri uri = Uri.parse("file://" + filePath);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(uri);

		// optionally:
		// intent.setPackage("org.openintents.filemanager");

		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Upload file to dropbox
	 */
	public void onClickFileDrop(View view) {
		delete = false;
		String fileName = "";

		Log.d("fileName", fileName);
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE,
				getString(R.string.open_title));// select file to open
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT,
				getString(R.string.open_button));// open

		try {
			startActivityForResult(intent, REQUEST_CODE_GET_FILE_FOR_DROP);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}

	}

	public void onClickDelFileDrop(View view) {
		delete = true;
		String fileName = "";

		Log.d("fileName", fileName);
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE,
				getString(R.string.open_title));// select file to open
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT,
				getString(R.string.open_button));// open

		try {
			startActivityForResult(intent, REQUEST_CODE_GET_FILE_FOR_DROP);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, R.string.no_filemanager_installed,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * This is called after the file manager finished.
	 */
	@SuppressWarnings("unused")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		mTextView.setText("");

		switch (requestCode) {

		case REQUEST_LINK_TO_DBX:
			if (resultCode == Activity.RESULT_OK) {
				try {
					dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr
							.getLinkedAccount());
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (delete)
					deletefilefromdropbox();
				else
					addfiletodropbox();
			}
			break;

		case REQUEST_CODE_GET_FILE_FOR_DROP:
			if (resultCode == RESULT_OK && data != null) {
				// obtain the filename
				Log.d("activity result",
						"returned from picking file or directory");
				Log.d("data", data.toString());
				Uri fileUri = data.getData();
				Log.d("uri", fileUri.toString());
				if (fileUri != null) {
					String filePath = fileUri.getPath();
					if (filePath != null) {
						mEditText.setText(filePath);
					}
					if (mDbxAcctMgr.hasLinkedAccount()) {
						try {
							dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr
									.getLinkedAccount());
						} catch (Unauthorized e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (delete)
							deletefilefromdropbox();
						else
							addfiletodropbox();

					} else {
						mDbxAcctMgr.startLink(this, REQUEST_LINK_TO_DBX);
						
					}

				} else {
					Toast.makeText(this, "empty uri", Toast.LENGTH_LONG).show();
				}
			}
			break;

		case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
			if (resultCode == RESULT_OK && data != null) {
				// obtain the filename
				Log.d("activity result",
						"returned from picking file or directory");
				Log.d("data", data.toString());
				Uri fileUri = data.getData();
				Log.d("uri", fileUri.toString());
				if (fileUri != null) {
					String filePath = fileUri.getPath();
					if (filePath != null) {
						mEditText.setText(filePath);
					}
				}
			}
			break;
		case REQUEST_CODE_GET_CONTENT:
			if (resultCode == RESULT_OK && data != null) {
				String filePath = null;
				long fileSize = 0;
				String displayName = null;
				Uri uri = data.getData();
				Cursor c = getContentResolver().query(
						uri,
						new String[] { MediaStore.MediaColumns.DATA,
								MediaStore.MediaColumns.MIME_TYPE,
								MediaStore.MediaColumns.DISPLAY_NAME,
								MediaStore.MediaColumns.SIZE }, null, null,
						null);
				if (c != null && c.moveToFirst()) {
					int id = c.getColumnIndex(Images.Media.DATA);
					if (id != -1) {
						filePath = c.getString(id);
					}
					displayName = c.getString(2);
					fileSize = c.getLong(3);
				}
				if (filePath != null) {
					mEditText.setText(filePath);
					String strFileSize = getString(R.string.get_content_info,
							displayName, "" + fileSize);
					mTextView.setText(strFileSize);
				}
			}
		}
	}

	private void deletefilefromdropbox() {
		// TODO Auto-generated method stub
		File myfile = new File(mEditText.getText().toString());
		String dummy[] = myfile.toString().split("/");
		// Log.d("file name", "file name");
		final String FILE_NAME = dummy[dummy.length - 1];
		DbxPath testPath = new DbxPath(DbxPath.ROOT, FILE_NAME);

		Toast.makeText(this, "file name " + FILE_NAME + " path " + testPath,
				Toast.LENGTH_LONG).show();
		try {
			if (dbxFs.exists(testPath)) {
				dbxFs.delete(testPath);

				Toast.makeText(this, "\ndeleted file '" + FILE_NAME + "'.\n",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(
						this,
						"\nfile doesn't exists with name'" + FILE_NAME + "'.\n",
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

	private void addfiletodropbox() {
		// TODO Auto-generated method stub

		File myfile = new File(mEditText.getText().toString());
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

}