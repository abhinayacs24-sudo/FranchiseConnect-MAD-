// Placeholder for Report Controller
exports.generateReport = async (req, res) => {
    try {
        res.json({ message: "Report generated" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
