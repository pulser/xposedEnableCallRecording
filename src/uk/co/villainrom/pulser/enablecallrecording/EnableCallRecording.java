package uk.co.villainrom.pulser.enablecallrecording;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import java.util.HashMap;

/* 
 * This patch will enable the Samsung call recording feature, which is normally enabled or disabled
 * based on the user's region. There are many regions where call recording is completely legal,
 * but which have not been enabled by Samsung. If user lives in one of these regions, they can use
 * this patch in order to restore the call recording feature.
 */

public class EnableCallRecording implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (lpparam.packageName.equals("com.android.phone")) {
			ClassLoader classLoader = lpparam.classLoader;
			//method by rovo89
			XposedHelpers.findAndHookMethod("com.android.phone.PhoneFeature", classLoader, "hasFeature", String.class, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					if ("voice_call_recording".equals(param.args[0])) {
						param.setResult(Boolean.TRUE);
						XposedBridge.log("setcallrecordtrue");
					}
				}
			});
		}


	};
}
