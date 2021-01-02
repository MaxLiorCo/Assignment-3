#include <iostream>
#include <connectionHandler.h>
#include <encdec.h>
#include <mutex>
#include <thread>

using namespace std;

//Thread Task that listens to keyboard input and sends to server
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

            if (!connectionHandler->sendBytes(toBytes.c_str(), toBytes.length())) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
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

    //activate a thread that listens to keyboard
    std::thread th1(&KeyboardListener::run, &kb);

    //Session with server
    while (session) {
        char opCodeArr[2];
        char messageOpCodeArr[2];
        //Waiting for server reply, BLOCKING
        if (!connectionHandler.getBytes(opCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        if (!connectionHandler.getBytes(messageOpCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        short opCode = encdec::decodeTwoBytes(opCodeArr);
        short opCodeMessage = encdec::decodeTwoBytes(messageOpCodeArr);
        //Received acknowledge message
        if(opCode == 12) {
            cout << "ACK " << opCodeMessage << endl;
            std::string str = "";
            if(opCodeMessage == 4) //Logout
                session = false;
            if (!encdec::decodeString(connectionHandler, str)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            //Cases which return a subsequent message to ACK
            else if((opCodeMessage == 6) | (opCodeMessage == 7) | (opCodeMessage == 8) | (opCodeMessage == 9) | (opCodeMessage == 11)) {
                cout << str << endl;
            }
        }
        //Received error message
        else if(opCode == 13){
            cout << "ERR " << opCodeMessage << endl;
        } else cout << "Incorrect OpCode returned" << endl;
    }
    //Session finished
    th1.detach();
    return 0;
}

