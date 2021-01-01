#include <iostream>
#include <connectionHandler.h>
#include <encdec.h>
#include <bitset>

using namespace std;


class KeyboardListener {
private:
    int _id;
    std::mutex&_mutex;
    bool& _session;
    ConnectionHandler* connectionHandler;
public:
    KeyboardListener(int id, std::mutex &mutex, bool& session, ConnectionHandler* ch) : _id(id), _mutex(mutex), _session(session), connectionHandler(ch) {}

    void run() {
        while(_session) {
            std::lock_guard<std::mutex> lock(_mutex);
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);

            std::string toBytes = encdec::encode(line);
            if (toBytes.empty()) {
                continue;
            }

            //TODO convert line to required byte array and length
            if (!connectionHandler->sendBytes(toBytes.c_str(), toBytes.length())) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            // connectionHandler.sendBytes()
            std::cout << "Sent " << toBytes.length() << " bytes to server" << std::endl;
        }
    }
};

int main(int argc, char *argv[]) {

    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    else
        std::cout << "Connection established to: " << host << ":" << port << std::endl;

    bool session = true;
    std::mutex mutex;
    KeyboardListener kb(1, mutex, session, &connectionHandler);

    std::thread th1(&KeyboardListener::run, &kb);

    //From here we will see the rest of the BGRS client implementation:
    while (session) {
        const short bufsize = 1024;
/*        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);

        std::string toBytes = encdec::encode(line);
        if (toBytes.empty()) {
            continue;
        }

        //TODO convert line to required byte array and length
        if (!connectionHandler.sendBytes( toBytes.c_str() , toBytes.length())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        // connectionHandler.sendBytes()
        std::cout << "Sent " <<  toBytes.length() << " bytes to server" << std::endl;*/
  //----------------------------------------------------------------------------------------------------
        char opCodeArr[2];
        char messageOpCodeArr[2];
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if (!connectionHandler.getBytes(opCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        if (!connectionHandler.getBytes(messageOpCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        cout << opCodeArr[0] + 0 << " " << opCodeArr[1] + 0<<endl;
        short opCode = encdec::decodeTwoBytes(opCodeArr);
        short opCodeMessage = encdec::decodeTwoBytes(messageOpCodeArr);
        if(opCode == 12){
            cout << "ACK " << opCodeMessage << endl;
            char buffer[bufsize];
            std::string str(buffer);
            if(opCodeMessage == 4) //Logout
                session = false;
            //TODO decide which opCodeMessages return additional strings (if required to do so)
            if (!connectionHandler.getFrameAscii(str, '\0')) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            else if(opCodeMessage == 6 | opCodeMessage == 7 | opCodeMessage == 8 | opCodeMessage == 9 | opCodeMessage == 11)
                cout << str << endl;

        }
        else if(opCode == 13){
            cout << "ERR " << opCodeMessage << endl;
        } else cout << "Incorrect OpCode returned" << endl;
    }
    std::cout << "session finished" << std::endl;
    th1.detach();
    return 0;
}

