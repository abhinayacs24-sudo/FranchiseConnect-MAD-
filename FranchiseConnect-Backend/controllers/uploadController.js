const path = require('path');

exports.uploadImage = async (req, res) => {
    try {
        if (!req.file) return res.status(400).json({ message: "No file uploaded" });

        const host = req.headers.host || 'localhost:5000';
        // Translate localhost or 127.0.0.1 to 10.0.2.2 for Android emulator network access
        const adjustedHost = (host.includes('localhost') || host.includes('127.0.0.1'))
            ? host.replace('localhost', '10.0.2.2').replace('127.0.0.1', '10.0.2.2')
            : host;
        const fileUrl = `${req.protocol}://${adjustedHost}/uploads/${req.file.filename}`;

        res.json({ message: "Image uploaded successfully!", url: fileUrl });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};
