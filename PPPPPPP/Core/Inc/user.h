#include "usart.h"

typedef struct User user, *pno;


struct User
{
	char nickName[15];
	char phoneNumber[9];
	pno (*getNickName)(user *); 
};


pno cd (user *me)
{	
	HAL_UART_Transmit_IT(&huart4, "AbcdefghijklmnopqrsutxZ", 23);
}
