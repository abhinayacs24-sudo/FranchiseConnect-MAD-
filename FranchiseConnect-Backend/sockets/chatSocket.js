const chatSocket = (io) => {
    io.on('connection', (socket) => {
        console.log('User connected to chat:', socket.id);

        socket.on('join_chat', (chatId) => {
            socket.join(chatId);
            console.log(`User joined chat: ${chatId}`);
        });

        socket.on('send_message', (data) => {
            io.to(data.chatId).emit('receive_message', data);
        });

        socket.on('disconnect', () => {
            console.log('User disconnected from chat');
        });
    });
};

module.exports = chatSocket;
