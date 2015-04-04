package utils;


import java.io.File;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;
 
/**
 * 音乐类型转化及时长获取工具类
 * @author boxiZen
 */

public class MusicUtil {
	public static void main(String[] args) {
		System.out.println(getDuration("res/sample.mp3"));
	}

	public static void oggToMp3(String sourceFile,String targetFile) {
		sourceFile = "sound.ogg";
		targetFile = "sound.mp3";
		String tempFile = "test.wav";
		String resPath = "res/";	
		String toWavCmd = "oggdec "+resPath+sourceFile+" -o "+resPath+tempFile;
		String toMp3Cmd = "lame "+resPath+tempFile+" "+resPath+targetFile;
		String delCmd = "rm "+resPath+tempFile;
		try {
			Runtime.getRuntime().exec(toWavCmd).waitFor();
			Runtime.getRuntime().exec(toMp3Cmd).waitFor();
			Runtime.getRuntime().exec(delCmd).waitFor();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public static int getDuration(String filePath){
		File file = new File(filePath);
		AudioFileFormat baseFileFormat = null;
		AudioFormat baseFormat = null;
		double totalDur;
		int duration=0;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(file);
			baseFormat = baseFileFormat.getFormat();
			if(baseFileFormat instanceof TAudioFileFormat){
				Map properties = ((TAudioFileFormat)baseFileFormat).properties();
				String key = "duration";
				if(properties.get(key) != null){
					totalDur = (Long)properties.get(key);
					duration = (int) Math.round(totalDur/1000000.0);
				}
			}
			if(baseFormat instanceof TAudioFormat){
				 Map properties = ((TAudioFormat)baseFormat).properties();
				 String key = "duration";
				 if(properties.get(key) != null){
					 totalDur = (Long)properties.get(key);
					 duration = (int) Math.round(totalDur/1000000.0);
					 System.out.println(duration+"s");
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return duration;
	}
}
