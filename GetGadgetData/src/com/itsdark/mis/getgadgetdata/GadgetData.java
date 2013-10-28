package com.itsdark.mis.getgadgetdata;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GadgetData implements Parcelable {

	public String name;
	public String size;
	public String wt;
	public String OS;
	public String connectivity;
	public String processor;
	public String camera;
	public String sd_storage;
	public String img;

	public GadgetData(String name, String size, String wt, String OS,
			String connectivity, String processor, String camera,
			String sd_storage, String img) {
		this.name = name;
		this.wt = wt;
		this.size = size;
		this.OS = OS;
		this.connectivity = connectivity;
		this.processor = processor;
		this.camera = camera;
		this.sd_storage = sd_storage;
		this.img = "img" + img;
	}

	public GadgetData(Parcel in) {
		String[] data = new String[9];
		in.readStringArray(data);
		
		this.name = data[0];
		this.wt = data[1];
		this.size = data[2];
		this.OS = data[3];
		this.connectivity = data[4];
		this.processor = data[5];
		this.camera = data[6];
		this.sd_storage = data[7];
		this.img = data[8];
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[] {
				this.name,
				this.wt,
				this.size,
				this.OS,
				this.connectivity,
				this.processor,
				this.camera,
				this.sd_storage,
				this.img
		});
	}

	public static final Parcelable.Creator<GadgetData> CREATOR = new Parcelable.Creator<GadgetData>() {

		@Override
		public GadgetData createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new GadgetData(in);
		}

		@Override
		public GadgetData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GadgetData[size];
		}
	};
}
