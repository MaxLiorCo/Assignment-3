//
// Created by spl211 on 31/12/2020.
//
#ifndef CLIENT_ENCDEC_H
#define CLIENT_ENCDEC_H
#include <iostream>

class encdec {
private:
    static void shortToBytes(short num, char* bytesArr);
    static short bytesToShort(char* bytesArr);
public:
    static std::string encode(std::string &line , int len);
    //decode here
};

#endif //CLIENT_ENCDEC_H
