package com.olegpoluliashchenko.vkgallery.application;

import com.olegpoluliashchenko.vkgallery.activity.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

public class VkApplication extends Application {

	VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
	    @Override
	    public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
		if (newToken == null) {
			Toast.makeText(getApplicationContext(), "AccessToken invalidated", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	    }
	};
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    vkAccessTokenTracker.startTracking();
	    VKSdk.initialize(this);
	}
	
}
