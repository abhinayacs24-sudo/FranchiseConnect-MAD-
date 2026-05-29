// Placeholder for Notification Controller
exports.getNotifications = async (req, res) => {
    try {
        res.json({ notifications: [] });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.markAsRead = async (req, res) => {
    try {
        res.json({ message: "Notification marked as read" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
