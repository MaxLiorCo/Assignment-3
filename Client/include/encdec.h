//
// Created by spl211 on 31/12/2020.
//
#ifndef CLIENT_ENCDEC_H
#define CLIENT_ENCDEC_H
#include <iostream>

class encdec {
private:
    void shortToBytes(short num, char* bytesArr);
    short bytesToShort(char* bytesArr);
public:
    std::string encode(std::string &line , int len);
    //decode here
};

#endif //CLIENT_ENCDEC_H
