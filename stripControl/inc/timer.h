#ifndef TIMER_H_
#define TIMER_H_

#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdint.h>

void timerInit();

extern volatile uint8_t timerFlag;

#endif /* TIMER_H_ */