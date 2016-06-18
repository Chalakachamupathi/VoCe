import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
/***
*This class create a dataline for capture audio and return the data line from 
*captureAudio method
*
***/
public class RecoderAndPlay{

private boolean stopCapture = false;
private AudioFormat audioFormat;
private TargetDataLine targetDataLine;
private SourceDataLine sourceDataLine;

public RecoderAndPlay(int check){

	switch (check){
		case 1:captureSetup();
			break;
		case 2:outPutSetup();
			break;
	}
	
	

}
	private AudioFormat getAudioFormat() {
	    float sampleRate = 16000.0F;
	    int sampleSizeInBits = 16;
	    int channels = 2;
	    boolean signed = true;
	    boolean bigEndian = true;
	    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}


	private void captureSetup(){
			try {
		            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
		            System.out.println("Available mixers:");
		            Mixer mixer = null;

		            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
		                    System.out.println(cnt + " " + mixerInfo[cnt].getName());
		                    mixer = AudioSystem.getMixer(mixerInfo[cnt]);

		                    Line.Info[] lineInfos = mixer.getTargetLineInfo();

		                    if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
		                            System.out.println(cnt + " Mic is supported!");
		                            break;
		                    }
		            }

		            audioFormat = getAudioFormat();     //get the audio format
		            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

		            targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
		            targetDataLine.open(audioFormat);
		            targetDataLine.start();

	         } catch (LineUnavailableException e) {
		            System.out.println(e);
		            e.printStackTrace();

		            System.exit(0);
			}
	}

	public TargetDataLine captureAudio(){
	    
	        return this.targetDataLine;
	       
	}

	private void outPutSetup(){
		  try {
	            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
	            System.out.println("Available mixers:");
	            Mixer mixer = null;
	            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
	                  System.out.println(cnt + " " + mixerInfo[cnt].getName());
	                  
	                  mixer = AudioSystem.getMixer(mixerInfo[cnt]);

	                  Line.Info[] lineInfos = mixer.getTargetLineInfo();
	                  if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
	                      //System.out.println(cnt + " Mic is supported!");
	                      break;
	                  }
	            }

	            audioFormat = getAudioFormat();     //get the audio format
	           

	            DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
	            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
	            sourceDataLine.open(audioFormat);
	            sourceDataLine.start();
	            
	            //Setting the maximum volume
	            FloatControl control = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
	            control.setValue(control.getMaximum());
	            
            } catch (Exception e) {
	            System.out.println(e);
	            e.printStackTrace();
	           
      		}
	}

	public void player(byte tempBuffer[]){	    

	    sourceDataLine.write(tempBuffer, 0, tempBuffer.length);   //playing audio available in tempBuffer
	           
	}

	

}
