//
// Created by spl211 on 31/12/2020.
//
#ifndef CLIENT_ENCDEC_H
#define CLIENT_ENCDEC_H
#include <iostream>
#include "connectionHandler.h"

class encdec {
private:
    static void shortToBytes(short num, char* bytesArr);
    static short bytesToShort(char* bytesArr);
public:
    static std::string encode(std::string &line);
    static short decodeTwoBytes(char replyOpCode[]);
    static bool decodeString(ConnectionHandler &ch, std::string &result);
};

#endif //CLIENT_ENCDEC_H
