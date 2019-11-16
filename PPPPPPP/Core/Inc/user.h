#include "usart.h"

typedef struct User user1, *pno;


struct User
{
	char nickName[15];
	char phoneNumber[9];
	pno (*xxx)(user1 *); 
};


pno cd (user1 *me)
{	
	HAL_UART_Transmit_IT(&huart4, "AbcdefghijklmnopqrsutxZ", 23);
}
