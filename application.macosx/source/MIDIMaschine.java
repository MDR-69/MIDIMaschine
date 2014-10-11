import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MIDIMaschine extends PApplet {

         //Import MIDI library

final String MIDI_BUS_CONFIGURATION_IN = "LPD8";
final String MIDI_BUS_CONFIGURATION_GUITAR_WING = "Livid Guitar Wing";
final String MIDI_BUS_CONFIGURATION_OUT_1 = "Bus 2";

//Pitches for messages coming from the pads + knobs
final int PITCH_PAD_MUTE             = 40;
final int PITCH_PAD_STUTTER_1        = 36;
final int PITCH_PAD_STUTTER_2        = 41;
final int PITCH_PAD_STUTTER_3        = 37;
final int PITCH_PAD_STUTTER_4        = 42;
final int PITCH_PAD_STUTTER_5        = 38;
final int PITCH_PAD_STUTTER_6        = 43;
final int PITCH_PAD_STUTTER_7        = 39;
final int CC_TURNADO_1               = 1;
final int CC_TURNADO_2               = 2;
final int CC_TURNADO_3               = 3;
final int CC_TURNADO_4               = 4;
final int CC_TURNADO_5               = 5;
final int CC_DEATHWISH               = 6;
final int CC_SIDECHAIN               = 7;
final int CC_PITCHUP_GR              = 8;

//Pitches for messages coming from the Guitar Wing
final int PITCH_WING_BIG_ROUND_BUTTON_1 = 36;
final int PITCH_WING_BIG_ROUND_BUTTON_2 = 37;
final int PITCH_WING_BIG_ROUND_BUTTON_3 = 38;
final int PITCH_WING_BIG_ROUND_BUTTON_4 = 39;
final int PITCH_WING_ARROW_NEXT         = 40;
final int PITCH_WING_ARROW_PREVIOUS     = 41;
final int PITCH_WING_SMALL_RECTANGLE_1  = 42;
final int PITCH_WING_SMALL_RECTANGLE_2  = 43;
final int PITCH_WING_SMALL_RECTANGLE_3  = 44;
final int PITCH_WING_SMALL_RECTANGLE_4  = 45;
final int PITCH_WING_SMALL_SWITCH_1     = 46;
final int PITCH_WING_SMALL_SWITCH_2     = 47;
final int PITCH_WING_SMALL_SWITCH_3     = 48;
final int PITCH_WING_SMALL_SWITCH_4     = 49;
final int PITCH_TOGGLE                  = 4;
final int CC_SMALL_FADER_1              = 1;
final int CC_SMALL_FADER_2              = 2;
final int CC_BIG_FADER                  = 3;

//Correspondance between the pad's notes and the CC messages to be sent to Maschine
final int CC_OUT_TURNADO_1           = 0;
final int CC_OUT_TURNADO_2           = 1;
final int CC_OUT_TURNADO_3           = 2;
final int CC_OUT_TURNADO_4           = 3;
final int CC_OUT_TURNADO_5           = 4;
final int CC_OUT_DEATHWISH           = 5;
final int CC_OUT_SIDECHAIN           = 6;
final int CC_OUT_PITCHUP_GR          = 7;
final int CC_OUT_MUTE                = 57;
final int CC_OUT_STUTTER_1           = 32;
final int CC_OUT_STUTTER_2           = 33;
final int CC_OUT_STUTTER_3           = 34;
final int CC_OUT_STUTTER_4           = 35;
final int CC_OUT_STUTTER_5           = 36;
final int CC_OUT_STUTTER_6           = 52;
final int CC_OUT_STUTTER_7           = 53;

//Maschine configuration - everyone the same due to a Maschine bug !
final int CHANNEL_GROUP_A1           = 15;    //MIDI Channel 16
final int CHANNEL_GROUP_B1           = 15;    //MIDI Channel 16
final int CHANNEL_GROUP_C1           = 15;    //MIDI Channel 16
final int CHANNEL_GROUP_D1           = 15;    //MIDI Channel 16
final int CHANNEL_GROUP_E1           = 15;    //MIDI Channel 16

final int DELTA_FILTER_MS = 50;
long lastMillisecond_turnado_1 = 0;
long lastMillisecond_turnado_2 = 0;
long lastMillisecond_turnado_3 = 0;
long lastMillisecond_turnado_4 = 0;
long lastMillisecond_turnado_5 = 0;
long lastMillisecond_turnado_6 = 0;
long lastMillisecond_turnado_7 = 0;
long lastMillisecond_turnado_8 = 0;
int lastValue_turnado_1 = 0;
int lastValue_turnado_2 = 0;
int lastValue_turnado_3 = 0;
int lastValue_turnado_4 = 0;
int lastValue_turnado_5 = 0;
int lastValue_turnado_6 = 0;
int lastValue_turnado_7 = 0;
int lastValue_turnado_8 = 0;

//Instanciate MIDI control object
MidiBus myBus;
MidiBus myBus2;


public void setup() {
  //Initialize MIDI Control object
  midiInit();
}

public void draw() {
  //Loop, no need to do anything
}

public void midiInit() {
  
  MidiBus.list(); 
  //Arguments to create the MidiBus : Parent Class, IN device, OUT device
  myBus  = new MidiBus(this, MIDI_BUS_CONFIGURATION_IN, MIDI_BUS_CONFIGURATION_OUT_1);
  myBus2 = new MidiBus(this, MIDI_BUS_CONFIGURATION_GUITAR_WING, MIDI_BUS_CONFIGURATION_OUT_1);
}


/////////////////////////////////////////////////
//////////////       NOTE ON       //////////////
/////////////////////////////////////////////////

public void noteOn(int channel, int pitch, int velocity, long timestamp, String bus_name) {
  // Receive a noteOn
  
  //Drumpad sub-keyboard
  if (bus_name == MIDI_BUS_CONFIGURATION_IN) {
    switch (pitch) {
      case PITCH_PAD_MUTE:       sendMidiOut_On_Mute();break;
      case PITCH_PAD_STUTTER_1:  sendMidiOut_On_Stutter1();break;
      case PITCH_PAD_STUTTER_2:  sendMidiOut_On_Stutter2();break;
      case PITCH_PAD_STUTTER_3:  sendMidiOut_On_Stutter3();break;
      case PITCH_PAD_STUTTER_4:  sendMidiOut_On_Stutter4();break;
      case PITCH_PAD_STUTTER_5:  sendMidiOut_On_Stutter5();break;
      case PITCH_PAD_STUTTER_6:  sendMidiOut_On_Stutter6();break;
      case PITCH_PAD_STUTTER_7:  sendMidiOut_On_Stutter7();break;
      default: break;
    }
  }
  
  //Guitar Wing
  //else if (bus_name == MIDI_BUS_CONFIGURATION_GUITAR_WING) {
  else {
    switch (pitch) {
      case PITCH_WING_BIG_ROUND_BUTTON_1: sendMidiOut_On_GuitarWing_Stutter1();break;
      case PITCH_WING_BIG_ROUND_BUTTON_2: sendMidiOut_On_GuitarWing_Stutter2();break;
      case PITCH_WING_BIG_ROUND_BUTTON_3: sendMidiOut_On_GuitarWing_Stutter3();break;
      case PITCH_WING_BIG_ROUND_BUTTON_4: sendMidiOut_On_GuitarWing_Stutter4();break;
      case PITCH_WING_SMALL_RECTANGLE_1:  sendMidiOut_On_GuitarWing_OneOctaveUp();break;
      case PITCH_WING_SMALL_RECTANGLE_2:  sendMidiOut_On_GuitarWing_TwoOctaveUp();break;
      case PITCH_WING_SMALL_RECTANGLE_3:  sendMidiOut_On_GuitarWing_Mute();break;
      default: break;
    }
  }
}

//////////////////////////////////////////////////
//////////////       NOTE OFF       //////////////
//////////////////////////////////////////////////


public void noteOff(int channel, int pitch, int velocity, long timestamp, String bus_name) {
  // Receive a noteOff
  
  //Drumpad sub-keyboard
  if (bus_name == MIDI_BUS_CONFIGURATION_IN) {
    switch (pitch) {
      case PITCH_PAD_MUTE:       sendMidiOut_Off_Mute();break;
      case PITCH_PAD_STUTTER_1:  sendMidiOut_Off_Stutter1();break;
      case PITCH_PAD_STUTTER_2:  sendMidiOut_Off_Stutter2();break;
      case PITCH_PAD_STUTTER_3:  sendMidiOut_Off_Stutter3();break;
      case PITCH_PAD_STUTTER_4:  sendMidiOut_Off_Stutter4();break;
      case PITCH_PAD_STUTTER_5:  sendMidiOut_Off_Stutter5();break;
      case PITCH_PAD_STUTTER_6:  sendMidiOut_Off_Stutter6();break;
      case PITCH_PAD_STUTTER_7:  sendMidiOut_Off_Stutter7();break;
      default: break;
    }
  }

  //Guitar Wing
  //else if (bus_name == MIDI_BUS_CONFIGURATION_GUITAR_WING) {
  else {
    switch (pitch) {
      case PITCH_WING_BIG_ROUND_BUTTON_1: sendMidiOut_Off_GuitarWing_Stutter1();break;
      case PITCH_WING_BIG_ROUND_BUTTON_2: sendMidiOut_Off_GuitarWing_Stutter2();break;
      case PITCH_WING_BIG_ROUND_BUTTON_3: sendMidiOut_Off_GuitarWing_Stutter3();break;
      case PITCH_WING_BIG_ROUND_BUTTON_4: sendMidiOut_Off_GuitarWing_Stutter4();break;
      case PITCH_WING_SMALL_RECTANGLE_1:  sendMidiOut_Off_GuitarWing_OneOctaveUp();break;
      case PITCH_WING_SMALL_RECTANGLE_2:  sendMidiOut_Off_GuitarWing_TwoOctaveUp();break;
      case PITCH_WING_SMALL_RECTANGLE_3:  sendMidiOut_Off_GuitarWing_Mute();break;
      default: break;
    }
  }
}

/////////////////////////////////////////////////
//////////////  CONTROLLER CHANGE  //////////////
/////////////////////////////////////////////////


public void controllerChange(int channel, int number, int value, long timestamp, String bus_name) {
  // Receive a controllerChange
  
  //Drumpad sub-keyboard
  if (bus_name == MIDI_BUS_CONFIGURATION_IN) {
    switch (number) {
      case CC_TURNADO_1:
        if (filterTimeElapsed(lastMillisecond_turnado_1) || value == 0) {
          sendMidiOut_CC_Turnado1(value);
          lastMillisecond_turnado_1 = System.currentTimeMillis();
        }
        break;
      case CC_TURNADO_2:  
        if (filterTimeElapsed(lastMillisecond_turnado_2) || value == 0) {
          sendMidiOut_CC_Turnado2(value);
          lastMillisecond_turnado_2 = System.currentTimeMillis();
        }
        break;
      case CC_TURNADO_3:
        if (filterTimeElapsed(lastMillisecond_turnado_3) || value == 0) {
          sendMidiOut_CC_Turnado3(value);
          lastMillisecond_turnado_3 = System.currentTimeMillis();
        }
        break;
      case CC_TURNADO_4:
        if (filterTimeElapsed(lastMillisecond_turnado_4) || value == 0) {
          sendMidiOut_CC_Turnado4(value);
          lastMillisecond_turnado_4 = System.currentTimeMillis();
        }
        break;
      case CC_TURNADO_5:  
        if (filterTimeElapsed(lastMillisecond_turnado_5) || value == 0) {
          sendMidiOut_CC_Turnado5(value);
          lastMillisecond_turnado_5 = System.currentTimeMillis();
        }
        break;
      case CC_DEATHWISH:
        if (filterTimeElapsed(lastMillisecond_turnado_6) || value == 0) {
          sendMidiOut_CC_Deathwish(value);
          lastMillisecond_turnado_6 = System.currentTimeMillis();
        }
        break;
      case CC_SIDECHAIN:
        if (filterTimeElapsed(lastMillisecond_turnado_7) || value == 0) {
          sendMidiOut_CC_Sidechain(value);
          lastMillisecond_turnado_7 = System.currentTimeMillis();
        }
        break;
      case CC_PITCHUP_GR:
        if (filterTimeElapsed(lastMillisecond_turnado_8) || value == 0) {
          if (value > 5) {
            sendMidiOut_CC_PitchupGR_On(value);
            lastMillisecond_turnado_8 = System.currentTimeMillis();
          }
          else {
            sendMidiOut_CC_PitchupGR_Off(value);
            lastMillisecond_turnado_8 = System.currentTimeMillis();
          }
        }
        break;
      default: break;    
    }
  }
  
  //Guitar Wing
  //else if (bus_name == MIDI_BUS_CONFIGURATION_GUITAR_WING) {
  else {    
    switch (number) {
      default: break;
    }
  }  
}

/////////////////////////////////////////////////
/////////////  FILTER TOOL FUNCTION  ////////////
/////////////////////////////////////////////////

public boolean filterTimeElapsed(long lastTimeStamp) {
  long delta = System.currentTimeMillis() - lastTimeStamp;
  if (delta > DELTA_FILTER_MS) {
    return true;
  }
  else {
    return false;
  }
}

/////////////////////////////////////////////////
/////////////  INDIVIDUAL FUNCTIONS  ////////////
/////////////////////////////////////////////////

public void sendMidiOut_On_Mute() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 2, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 3, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 4, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 5, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 6, 127);
}

public void sendMidiOut_On_Stutter1() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 7, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 8, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 9, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 10, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 11, 127);
}

public void sendMidiOut_On_Stutter2() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 12, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 13, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 14, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 15, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 16, 127);
}

public void sendMidiOut_On_Stutter3() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 17, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 18, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 19, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 20, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 21, 127);
}

public void sendMidiOut_On_Stutter4() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 22, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 23, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 24, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 25, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 26, 127);
}

public void sendMidiOut_On_Stutter5() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 27, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 28, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 29, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 30, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 31, 127);
}

public void sendMidiOut_On_Stutter6() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 32, 127);
}

public void sendMidiOut_On_Stutter7() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 33, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 34, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 35, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 36, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 37, 127);
}

////////////////////////////////////

public void sendMidiOut_On_GuitarWing_Stutter1() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 15, 127);
}

public void sendMidiOut_On_GuitarWing_Stutter2() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 25, 127);
}

public void sendMidiOut_On_GuitarWing_Stutter3() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 30, 127);
}

public void sendMidiOut_On_GuitarWing_Stutter4() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 32, 127);
}

public void sendMidiOut_On_GuitarWing_Mute() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 5, 127);
}

public void sendMidiOut_On_GuitarWing_OneOctaveUp() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, 63);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 127);
}

public void sendMidiOut_On_GuitarWing_TwoOctaveUp() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 127);
}


////////////////////////////////////
////////////////////////////////////

public void sendMidiOut_Off_Mute() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 2, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 3, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 4, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 5, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 6, 0);
}

public void sendMidiOut_Off_Stutter1() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 7, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 8, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 9, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 10, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 11, 0);
}

public void sendMidiOut_Off_Stutter2() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 12, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 13, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 14, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 15, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 16, 0);
}

public void sendMidiOut_Off_Stutter3() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 17, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 18, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 19, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 20, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 21, 0);
}

public void sendMidiOut_Off_Stutter4() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 22, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 23, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 24, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 25, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 26, 0);
}

public void sendMidiOut_Off_Stutter5() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 27, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 28, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 29, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 30, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 31, 0);
}

public void sendMidiOut_Off_Stutter6() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 32, 0);
}

public void sendMidiOut_Off_Stutter7() {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 33, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 34, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 35, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 36, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 37, 0);
}

////////////////////////////////////

public void sendMidiOut_Off_GuitarWing_Stutter1() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 15, 0);
}

public void sendMidiOut_Off_GuitarWing_Stutter2() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 25, 0);
}

public void sendMidiOut_Off_GuitarWing_Stutter3() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 30, 0);
}

public void sendMidiOut_Off_GuitarWing_Stutter4() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 32, 0);
}

public void sendMidiOut_Off_GuitarWing_Mute() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 5, 0);
}

public void sendMidiOut_Off_GuitarWing_OneOctaveUp() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 0);
}

public void sendMidiOut_Off_GuitarWing_TwoOctaveUp() {
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 0);
}

////////////////////////////////////////////////////////////////////////////

public void sendMidiOut_CC_Turnado1(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 38, value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 39, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 40, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 41, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 42, value);
}

public void sendMidiOut_CC_Turnado2(int value) {
  //myBus.sendControllerChange(CHANNEL_GROUP_A1, CC_OUT_TURNADO_2, value);
  //Exception ! The MPKMiniMk2 sends CC1 with the joystick
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 43, value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 44, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 45, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 46, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 47, value);
}

public void sendMidiOut_CC_Turnado3(int value) {
//  myBus.sendControllerChange(CHANNEL_GROUP_A1, 48, value);
//  myBus.sendControllerChange(CHANNEL_GROUP_B1, 49, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 50, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 51, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 52, value);
}

public void sendMidiOut_CC_Turnado4(int value) {
//  myBus.sendControllerChange(CHANNEL_GROUP_A1, 53, value);
//  myBus.sendControllerChange(CHANNEL_GROUP_B1, 54, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 55, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 56, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 57, value);
}

public void sendMidiOut_CC_Turnado5(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 58, value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 59, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 60, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 61, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 62, value);
}

public void sendMidiOut_CC_Deathwish(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 63, value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 64, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 65, value);
  //myBus.sendControllerChange(CHANNEL_GROUP_D1, CC_OUT_DEATHWISH+24, value);
}

public void sendMidiOut_CC_Sidechain(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 66, 127-value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 67, value);
  //myBus.sendControllerChange(CHANNEL_GROUP_C1, CC_OUT_SIDECHAIN+16, value);
  //myBus.sendControllerChange(CHANNEL_GROUP_D1, CC_OUT_SIDECHAIN+24, value);
}

public void sendMidiOut_CC_PitchupGR_On(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 68, value);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 69, value);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 70, value);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, value);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 72, value);
  
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 73, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 74, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 75, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 127);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 77, 127);
}

public void sendMidiOut_CC_PitchupGR_Off(int value) {
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 68, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 69, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 70, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 71, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 72, 0);
  
  myBus.sendControllerChange(CHANNEL_GROUP_A1, 73, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_B1, 74, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_C1, 75, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_D1, 76, 0);
  myBus.sendControllerChange(CHANNEL_GROUP_E1, 77, 0);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MIDIMaschine" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
