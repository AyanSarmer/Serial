#include "timer.h"

volatile uint8_t timerFlag = 0;

ISR(TIMER1_OVF_vect) 
{
    timerFlag = 1;
}

void timerInit() 
{
    TCCR1A = 0;
    TIMSK1 = (1 << TOIE1);
    sei();
    TCCR1B = (1 << CS12); //| (1 << CS10);  
}