// Placeholder for Review Controller
exports.addReview = async (req, res) => {
    try {
        res.json({ message: "Review added" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.getBrandReviews = async (req, res) => {
    try {
        res.json({ reviews: [] });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
