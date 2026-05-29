// Placeholder for Chat Controller
exports.getMessages = async (req, res) => {
    try {
        res.json({ message: "Chat messages fetched" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.sendMessage = async (req, res) => {
    try {
        res.json({ message: "Message sent" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
