package br.com.advocacia.service;

import java.util.Locale;

public class OSDetection {
    public enum OSType{
        Windows, Linux
    };

    protected static OSType detectedOS;

    public static OSType getSystem(){
        if(detectedOS == null){
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if((OS.indexOf("linux")>=0)){
                detectedOS = OSType.Linux;
            }else if ((OS.indexOf("win")>=0)){
                detectedOS = OSType.Windows;
        }
    }
    return detectedOS;
}}
