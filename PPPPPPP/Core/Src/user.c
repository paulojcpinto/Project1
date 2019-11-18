#include "user.h"

#define discard_char					UART3Tx_index++

#define char_trama_init 			( char )		'<' 
#define char_trama_end  			( char ) 		'>'	
	
#define char_trama_nickName 	( char ) 		'N'
#define int_nickName					(  int )		2

	static int out_index = 0;

void receive_nickName (user *me, int *c)
{
	*c = int_nickName;
//	HAL_UART_Transmit_IT(&huart4, "okoko", 5);
	me->nickName[out_index++] = char_trama_init;
	me->nickName[out_index++] = UART3Rx_Buffer[UART3Tx_index++];
}

void prepare_receive_info( user *me, int *c )
{
	switch ( UART3Rx_Buffer[UART3Tx_index] )
	{
		case char_trama_nickName:
		{
			*c= 2;
			receive_nickName (me, c);
		};break;
	}
}

void end_receiving_trama ( user *me, int *c)
{
	switch (*c)
			{
				case int_nickName:
				{
					me->nickName[out_index++] = UART3Rx_Buffer[UART3Tx_index++];
					HAL_UART_Transmit_IT(&huart4, me->nickName, out_index);

				}; break;
			}
	*c = -1;
}

void save_char ( user *me, int *c )
{
	switch (*c)
			{
				case int_nickName:
				{
					me->nickName[out_index++] = UART3Rx_Buffer[UART3Tx_index++];
				}; break;
			}
}

int getNickName (user *me, int *c)
{

	
	while(UART3Tx_index != UART3Rx_index)
	{
		if(UART3Rx_Buffer[UART3Tx_index] == char_trama_init)
		{
			*c = 1;
			out_index = 0;
			UART3Tx_index ++;
		}
		else if (*c == 1)
		{
			prepare_receive_info( me, c);
		}
		else if (UART3Rx_Buffer[UART3Tx_index] == char_trama_end)
		{
			end_receiving_trama ( me, c);
		}
		else if (*c > 1)			
		{
			save_char( me, c );
		}
		else
			discard_char;
		
		out_index &= ~(1<<7);
		UART3Tx_index &= ~(1<<7);
	}
	return *c;
}
