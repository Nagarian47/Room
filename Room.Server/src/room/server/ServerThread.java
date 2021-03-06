/*
 * To change this license header, choose License Headers socketIn Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template socketIn the editor.
 */
package room.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import org.json.simple.parser.ParseException;
import room.ddl.Packet;
import room.ddl.PacketStatusEnum;
import room.ddl.Room;
import room.ddl.exception.ClientAlreadyConnectedException;

/**
 *
 * @author Benoît
 */
class ServerThread implements Runnable {

    private final Socket socket;
    private final String sourceToString;

    public ServerThread(Socket _socket) {
        socket = _socket;
        sourceToString = socket.getInetAddress().toString() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try {
            System.out.println("Connexion entrante depuis :" + sourceToString);

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);

            try {
                Packet incomingMessage = new Packet(socketIn.readLine());
                incomingMessage.getUserInfo().getOwnAddress().setIp(socket.getInetAddress().getHostAddress().replace("\\", "").replace("/", ""));
                incomingMessage.getUserInfo().getOwnAddress().setPort(socket.getPort());

                switch (incomingMessage.getPacketStatus()) {
                    case Connection:
                        try {
                            RoomServer.serverLounge.addClient(incomingMessage.getUserInfo());
                            //RoomServer.serverLounge.addRoom("Bobby", incomingMessage.getUserInfo());
                            socketOut.println(new Packet(incomingMessage, RoomServer.serverLounge.getRoomsToJson().toJSONString()).toString());
                        } catch (ClientAlreadyConnectedException ex) {
                            socketOut.println(new Packet(incomingMessage, ex.getLocalizedMessage(), PacketStatusEnum.Error).toString());
                        }
                        break;

                    case Disconnection:
                        RoomServer.serverLounge.removeClient(incomingMessage.getUserInfo());
                        //socketOut.println(new Packet(incomingMessage, ""));
                        break;
                        
                    case GetRooms:
                        socketOut.println(new Packet(incomingMessage, RoomServer.serverLounge.getRoomsToJson().toJSONString()).toString());
                        break;
                        
                    case EnterRoom:
                        Room room = RoomServer.serverLounge.connectToRoom(incomingMessage.getUserInfo(), new Room(incomingMessage.getMessage()));
                        socketOut.println(new Packet(incomingMessage, room.toJson().toJSONString()).toString());
                        break;

                    case ExitRoom:
                        RoomServer.serverLounge.disconnectToRoom(incomingMessage.getUserInfo(), new Room(incomingMessage.getMessage()));
                        //socketOut.println(new Packet(incomingMessage, ""));
                        break;

                    case SendMessage:
                        try {
                            RoomServer.serverLounge.sendMessage(incomingMessage);
                            //socketOut.println(new Packet(incomingMessage, ""));
                        } catch (Exception ex) {
                            socketOut.println(new Packet(incomingMessage, ex.getLocalizedMessage(), PacketStatusEnum.Error).toString());
                        }
                        break;

                    default:
                        socketOut.println(new Packet((Packet) null, "InvalidMessage", PacketStatusEnum.Invalid));
                        break;
                }

            } catch (ParseException e) {
                System.err.println(sourceToString + " a produit l'erreur " + e.getLocalizedMessage());
                System.err.println(Arrays.toString(e.getStackTrace()));
                socketOut.println(new Packet((Packet) null, "InvalidMessage", PacketStatusEnum.Invalid));
            } finally {
                socketIn.close();
                socketOut.close();
                socket.close();
            }

            System.out.println("Fermeture de la connexion " + sourceToString);
        } catch (IOException ex) {
            System.err.println(sourceToString + " a produit l'erreur " + ex.getLocalizedMessage());
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
