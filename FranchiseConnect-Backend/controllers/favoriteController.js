const Favorite = require('../models/favourite');

exports.addToFavorites = async (req, res) => {
    try {
        const { userId, brandId } = req.body;

        // Validation: Ensure IDs are provided
        if (!userId || !brandId) {
            return res.status(400).json({ message: "userId and brandId are required" });
        }

        const existing = await Favorite.findOne({ userId, brandId });
        if (existing) return res.status(400).json({ message: "Already in favorites" });

        const newFavorite = new Favorite({ userId, brandId });
        await newFavorite.save();
        res.status(201).json({ message: "Added to favorites" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.getUserFavorites = async (req, res) => {
    try {
        const favorites = await Favorite.find({ userId: req.params.userId }).populate('brandId');

        // Filter out any favorites where the brand might have been deleted (brandId is null)
        const brandList = favorites
            .filter(f => f.brandId != null)
            .map(f => f.brandId);

        res.json(brandList);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

exports.removeFromFavorites = async (req, res) => {
    try {
        const { userId, brandId } = req.params;
        const deleted = await Favorite.findOneAndDelete({ userId, brandId });

        if (!deleted) {
            return res.status(404).json({ message: "Favorite not found" });
        }

        res.json({ message: "Removed from favorites" });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
