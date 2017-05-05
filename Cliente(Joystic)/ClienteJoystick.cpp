#include <cstdio>       //printf
#include <cstring>      //memset
#include <cstdlib>      //exit
#include <netinet/in.h> //htons
#include <arpa/inet.h>  //inet_addr
#include <sys/socket.h> //socket
#include <unistd.h>     //close
#include <string>
#include <sstream>
#include <iostream>
#include <unistd.h>

#include "Joystick.cpp"

#define MAXMSG 1024
#define PORTNUM 4325

int main(int argc, char *argv[])
{

	Joystick j1;

    while(1){

        int     sockfd;
        struct sockaddr_in servaddr;

        memset(&servaddr, 0x00, sizeof(servaddr));
        servaddr.sin_family = AF_INET;
        servaddr.sin_port = htons(PORTNUM);
        inet_pton(AF_INET, "192.168.7.1", &servaddr.sin_addr);

        sockfd = socket(AF_INET, SOCK_STREAM, 0);

        if (sockfd == -1)
        {
            std::cout << "Falha ao criar o socket" << std::endl;
            exit(EXIT_FAILURE);
        }

        if(connect(sockfd, (struct sockaddr *) &servaddr, sizeof(servaddr)) ==-1){
            std::cout << "Jogo finalizado" << std::endl;
            exit(EXIT_SUCCESS);
        }

        std::stringstream buffer;

        buffer << "A: " << j1.escolherAngulo() << " B: " << j1.valorBotao() << " F: " << j1.valorFotossensor();

        //Escrevendo no canal de comunicação do socket
        if (send(sockfd, buffer.str().c_str(), buffer.str().length(),0)<0){
            std::cout << "Jogo finalizado" << std::endl;
            exit(EXIT_SUCCESS);
        }else{
        	std::cout << "Enviado para o socket: " << buffer.str() << std::endl;
        }
       	
        close(sockfd);
    
        usleep(100000);
    }
        
    return 0;
}