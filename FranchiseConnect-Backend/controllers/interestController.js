// Placeholder for Interest Controller
exports.expressInterest = async (req, res) => {
    try {
        res.json({ message: "Interest expressed successfully" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.getUserInterests = async (req, res) => {
    try {
        res.json({ interests: [] });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
