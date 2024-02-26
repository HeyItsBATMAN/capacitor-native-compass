package dev.heyitsbatman.plugins.nativecompass;

import android.app.Activity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "NativeCompass")
public class NativeCompassPlugin extends Plugin {
    private NativeCompass implementation;

    @Override
    public void load() {
        this.implementation = new NativeCompass(getActivity());
    }

     @Override
     public void handleOnResume() {
        super.handleOnResume();
        this.implementation.registerListeners();
     }

     @Override
     public void handleOnPause() {
        super.handleOnPause();
        this.implementation.unregisterListeners();
     }

    @PluginMethod
    public void getCurrentHeading(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", implementation.getCurrentHeading());
        call.resolve(ret);
    }
}
