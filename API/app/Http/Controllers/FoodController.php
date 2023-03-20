<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use App\Models\Food;

class FoodController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    public function getAll(Request $request){
        return $this->verifyToken($request, function(Request $request){
            $dataFood = Food::Where('name', 'like', '%'.$request->get('name').'%')->get();
            return response()->json([
                'error' => false,
                'message' => '',
                'data' => $dataFood
            ], 200);
        });
    }
}
