<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\FitnessMotion;

class FitnessController extends Controller
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

    public function getAll(Request $request)
    {
        return $this->verifyToken($request, function (Request $request) {
            $dataFood = FitnessMotion::Where('name', 'like', '%' . $request->get('name') . '%')->get();
            return response()->json([
                'error' => false,
                'message' => '',
                'data' => $dataFood
            ], 200);
        });
    }

    public function scanfitness(Request $request)
    {
        return $this->verifyToken($request, function (Request $request) {
            if ($request->hasFile('video') && $request->file('video')->isValid()) {
                $video = $request->file('video');
                $extensionAllowed = ['mp4', 'avi'];
                $extension = $request->video->extension();
                if (in_array($extension, $extensionAllowed)) {
                    $video->move(base_path('../video'), $video->getClientOriginalName());
                    return response()->json([
                        'error' => false,
                        'message' => 'Success.',
                    ], 200);
                } else {
                    return response()->json([
                        'error' => true,
                        'message' => 'Video tidak valid.',
                    ], 200);
                }
            } else {
                return response()->json([
                    'error' => true,
                    'message' => 'Gagal menyimpan video.',
                ], 200);
            }
        });
    }
}
