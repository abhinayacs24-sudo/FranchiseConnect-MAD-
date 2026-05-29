const express = require('express');
const router = express.Router();
const Brand = require('../models/brandModel');
const User = require('../models/userModel');

router.get('/brands/count', async (req, res) => {
    const count = await Brand.countDocuments();
    res.json({ count });
});

router.get('/users/count', async (req, res) => {
    const count = await User.countDocuments();
    res.json({ count });
});

module.exports = router;
