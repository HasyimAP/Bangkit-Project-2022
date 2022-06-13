<?php

/** @var \Laravel\Lumen\Routing\Router $router */

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return response()->json([
        'error' => false,
        'message' => 'FitVerse RestAPI'
    ]);
});

$router->post('/login', 'AuthController@login');
$router->post('/logout', 'AuthController@logout');
$router->post('/register', 'AuthController@register');


$router->get('/foods', 'FoodController@getAll');


$router->get('/fitness', 'FitnessController@getAll');
$router->post('/fitness', 'FitnessController@scanfitness');
