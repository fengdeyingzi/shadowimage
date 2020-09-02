package com.xl.game.view;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Terminal extends Console{
    private Process mProcess;
    public Terminal(Handler handler, ConsoleCallback callback,Context context) {
        super(handler, callback,context);
    }

    @Override
    public InputStream getInputStream() {
        //createProcessIfNeed();
        return mProcess.getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
        //createProcessIfNeed();
        return mProcess.getErrorStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return mProcess.getOutputStream();
    }

    private synchronized void createProcessIfNeed(){
        if(mProcess==null)
            try {
                String shell="sh";
                mProcess=Runtime.getRuntime().exec(shell);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void doProcess() {
        createProcessIfNeed();
    }

    @Override
    public void onDestory() {
        Log.i(TAG, "Terminal onDestory");
        if(mProcess!=null)
        {
			/*
			 try {
			 byte data[]=new byte[1];
			 data[0]=0x03;
			 mProcess.getOutputStream().write(data);//����Ctrl+c
			 mProcess.getOutputStream().flush();
			 } catch (IOException e) {
			 e.printStackTrace();
			 }*/
            mProcess.destroy();
            try {
                mProcess.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mProcess=null;
            mIsAlive=false;
        }
    }

    @Override
    public void onCreatedProcess() {
        String appendEnvPath = "";
        //XLDEBUG
        //Busybox.getWorkDir(mContext)+":"
        //	+GNUCCompiler.getAbiPath(mContext)+":"
        //	+GNUCCompiler.getGccPath(mContext)+":"
        //	+GNUCCompiler.getCcPath(mContext)+":";
        File f=mContext.getFilesDir();
        //String path_include = new File(filename).getParent();
        //ShellUtils.execCommand("cp -f \""+filename+"\" \""+f.getAbsolutePath()+File.separator+"temp.c\"",false);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("export GCCHOME="+mContext.getFilesDir().getPath()+"/gcc\nexport GCCPATH=$GCCHOME/bin:$GCCHOME/arm-linux-androideabi/bin:$GCCHOME/libexec/\nexport PATH=$PATH:$GCCHOME:$GCCPATH\n");
//        stringBuilder.append("chmod 755 "+f.getPath()+"/gcc/bin/*\n");
//        stringBuilder.append("chmod 755 "+f.getPath()+"/gcc/arm-linux-androideabi/bin/*\n");
//        stringBuilder.append("chmod 755 "+f.getPath()+"/gcc/libexec/gcc/arm-linux-androideabi/7.2.0/*\n");


        execute(stringBuilder.toString());
    }
}
