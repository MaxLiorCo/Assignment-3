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
    //encodes message to server acording to protocol BGRSprotocol
    static std::string encode(std::string &line);
    //used to decode Opcode & Opcode message
    static short decodeTwoBytes(char replyOpCode[]);
    //decodes the message provided after the ACK reply
    static bool decodeString(ConnectionHandler &ch, std::string &result);
};

#endif //CLIENT_ENCDEC_H
