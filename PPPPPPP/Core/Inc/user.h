#include "usart.h"

typedef struct User user;


struct User
{
	char nickName[15];
	char phoneNumber[9];
	int (*getNickName)(user *, uint8_t *); 
};


void cd (user *me)
{	
	HAL_UART_Transmit_IT(&huart4, "AbcdefghijklmnopqrsutxZ", 23);
}

int getNickName (user *me, uint8_t *c)
{
	static int last_index = 0;
	static int out_index = 0;
	int return_value = 0;
	while(UART3Tx_index != UART3Rx_index){
		if(UART3Rx_Buffer[UART3Tx_index] == '<'){
			*c = 3;
			UART3Tx_index ++;
		}
		else if (*c == 1)
		{
		if(UART3Rx_Buffer[UART3Tx_index] == '>') {	
			*c = 2;
			Rx_Buffer[out_index] = UART3Rx_Buffer[UART3Tx_index++];
			UART3Tx_index &= ~(1<<7);
			out_index=0;
			return *c-2;

		}
		else
		Rx_Buffer[out_index++] = UART3Rx_Buffer[UART3Tx_index++];
	}
		else if (*c == 3 && UART3Rx_Buffer[UART3Tx_index] == 'N')
		{
			*c=1;
			HAL_UART_Transmit_IT(&huart4, "okoko", 5);
			Rx_Buffer[out_index++] = '<';
			Rx_Buffer[out_index++] = UART3Rx_Buffer[UART3Tx_index++];
		}
		else 
		{
			*c= 7;
			UART3Tx_index++;
		}
		//UART3Tx_index++;
		UART3Tx_index &= ~(1<<7);
	}
	return *c-2;
}
