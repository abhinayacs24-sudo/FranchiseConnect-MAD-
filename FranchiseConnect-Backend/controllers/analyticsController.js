// Placeholder for Analytics Controller
exports.getStats = async (req, res) => {
    try {
        res.json({ message: "Analytics data fetched" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
