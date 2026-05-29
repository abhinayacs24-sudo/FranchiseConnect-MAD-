const express = require('express');
const router = express.Router();
const favoriteController = require('../controllers/favoriteController');

router.post('/', favoriteController.addToFavorites);
router.get('/:userId', favoriteController.getUserFavorites);
router.delete('/:userId/:brandId', favoriteController.removeFromFavorites);

module.exports = router;
