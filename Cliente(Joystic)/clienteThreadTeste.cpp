#include <cstdio>       //printf
#include <cstring>      //memset
#include <cstdlib>      //exit
#include <netinet/in.h> //htons
#include <arpa/inet.h>  //inet_addr
#include <sys/socket.h> //socket
#include <unistd.h>     //close
#include <string>
#include <sstream>

#define MAXMSG 1024
#define MAXNAME 100
#define PORTNUM 4325

int main(int argc, char *argv[])
{

    for(int i=0;i<10;i++){

        int     sockfd;
        struct sockaddr_in servaddr;

        memset(&servaddr, 0x00, sizeof(servaddr));
        servaddr.sin_family = AF_INET;
        servaddr.sin_port = htons(PORTNUM);
        inet_pton(AF_INET, "127.0.0.1", &servaddr.sin_addr);

        sockfd = socket(AF_INET, SOCK_STREAM, 0);

        if (sockfd == -1)
        {
            printf("Falha ao executar socket()\n");
            exit(EXIT_FAILURE);
        }

        if(connect(sockfd, (struct sockaddr *) &servaddr, sizeof(servaddr)) ==-1){
            printf("Falha ao executar connect()\n");
            exit(EXIT_FAILURE);
        }

        //from this point you can start write to the server and wait for its respose

        std::stringstream buffer;

        buffer << "Opa como vai? " << i;

        //Escrevendo no canal de comunicação do socket
        if (send(sockfd, buffer.str().c_str(), buffer.str().length(),0)<0){
            printf("Erro ao escrever no socket\n");
            exit(EXIT_FAILURE);
        }
        
        close(sockfd);
    }
        
    return 0;
}