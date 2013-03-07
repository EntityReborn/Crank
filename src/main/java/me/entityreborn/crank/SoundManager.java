/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.entityreborn.crank;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class SoundManager {
    private static SoundManager instance;
    
    public static SoundManager get() {
        if (instance == null) {
            instance = new SoundManager();
        }
        
        return instance;
    }
    
    private SoundManager() {
    }

    public void playSound(String filename) {
        URL url = SoundManager.class.getResource("/sounds/" + filename);
        (new PlayThread(url)).start();

    }

    private class PlayThread extends Thread {

        SourceDataLine sdl;
        AudioFormat format;
        AudioInputStream ais;
        byte tempBuffer[] = new byte[10000];

        private PlayThread(URL soundfile) {
            try {
                ais = AudioSystem.getAudioInputStream(soundfile);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            format = ais.getFormat();

            DataLine.Info dli = new DataLine.Info(SourceDataLine.class, format);

            try {
                sdl = (SourceDataLine) AudioSystem.getLine(dli);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            if (ais == null) {
                return;
            }
            
            try {
                sdl.open(format);
                sdl.start();

                int cnt;
                
                while ((cnt = ais.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        sdl.write(tempBuffer, 0, cnt);
                    }
                }

                sdl.drain();
                sdl.close();
            } catch (IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
