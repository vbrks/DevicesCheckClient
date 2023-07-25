# DevicesCheckClient

This is a client application that sends data about connected devices to a server that is associated with a telegram bot.
This application is expected to run on guest computers in the club or in  office.

Used technologies and libraries: netty, usb4java.

The application must be placed in a separate folder without spaces and cyrillic in the name and add the .jar file to autoload, for example, using the task scheduler and specify the server ip address as the first parameter. Once enabled, the client will wait for a connection to the server, after which it will create a configuration file.

The server part is located in the repository at the link: https://github.com/vbrks/DevicesCheckBot
