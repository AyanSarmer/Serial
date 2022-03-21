#include "device.h"

uint8_t buf[LEDS_NUM];
uint8_t	stripState = STRIP_OFF;
uint8_t deviceState = MAIN;

uint8_t redValue, greenValue, blueValue;

void deviceInit()
{
	serialBegin(); 
	STRIP_DDR |= (1 << STRIP_PIN); 
	stripOff();
	DS18B20_Init();
	timerInit();
}

void deviceControl()
{
    if(receiveFlag)
	{
		receiveFlag = 0;
		if(receivedByte[0] == 87)
		{
			greenValue = receivedByte[1];
			redValue = receivedByte[2];
			blueValue = receivedByte[3];

			stripSetColor();
		}	
	}

	if(timerFlag)
	{
		uint8_t temperatureBuff[4];
		timerFlag = 0;
		float currentTemperature = DS18B20_ReadTemperature();

		if(temperatureSign == PLUS)
		{
			temperatureBuff[0] = 0;
		}
		else if(temperatureSign == MINUS)
		{
			temperatureBuff[0] = 1;
		}
		
		temperatureBuff[1] = (uint8_t)(currentTemperature / 10);
		temperatureBuff[2] = (uint8_t)(currentTemperature - temperatureBuff[1] * 10);
		temperatureBuff[3] = (uint8_t)(currentTemperature * 10 - temperatureBuff[1] * 100 - temperatureBuff[2] * 10);
		for(int byteNum = 0; byteNum < 4; byteNum++)
		{
			serialSendByte(temperatureBuff[byteNum]);
		}	
	}
}

void stripOff()
{
	memset(buf, 0, sizeof(buf));
	stripRefresh(buf, sizeof(buf));
}

void stripSetColor()
{
	for(uint8_t ledNum = 0; ledNum < STRIP_LENGTH; ledNum++)
	{
		setLedColor(buf, ledNum, redValue, greenValue, blueValue);
	}
	stripRefresh(buf, sizeof(buf));
}

void setLedColor(uint8_t* p_buf, uint8_t led, uint8_t red, uint8_t green, uint8_t blue)
{
	uint16_t index = 3 * led;
	p_buf[index++] = red;
	p_buf[index++] = green;
	p_buf[index] = blue;
}