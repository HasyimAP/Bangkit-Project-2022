<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use App\Models\User;
use App\Models\Auth;

class AuthController extends Controller
{
    public function __construct()
    {
        //
    }

    public function login(Request $request)
    {
        $email = $request->get('email');
        $password = $request->get('password');
        $user = User::where('email', $email)->first();
        if ($user == null) {
            echo json_encode([
                'error' => true,
                'message' => 'User tidak ditemukan!'
            ]);
        } else {
            if (Hash::check($password, $user->makeVisible('password')->toArray()['password'])) {
                $token = hash("sha256", $user->id . date('Y-m-d hh:mm:ss'));
                Auth::create([
                    'id_user' => $user->id,
                    'token' => $token
                ]);
                echo json_encode([
                    'error' => false,
                    'message' => 'Berhasil login!',
                    'data' => [
                        'token' => $token,
                        'name' => $user->name,
                        'id' => $user->id,
                    ]
                ]);
            } else {
                echo json_encode([
                    'error' => true,
                    'message' => 'Password tidak benar!'
                ]);
            }
        }
    }

    public function logout(Request $request)
    {
        return $this->verifyToken($request, function (Request $request, $token) {
            $auth = Auth::where('token', $token)->first();
            if ($auth != null) {
                $auth->delete();
            }
            return response()->json([[
                'error' => true,
                'message' => 'Berhasil logout!'
            ]], 200);
        });
    }

    public function register(Request $request)
    {
        $name = $request->get('name');
        $gender = $request->get('gender');
        $email = $request->get('email');
        $password = $request->get('password');
        if (isset($name) && trim($name) != "" && isset($email) && trim($email) != "" && isset($password) && trim($password) != "" && isset($gender) && trim($gender) != "") {
            try {
                $res = User::create([
                    'name' => $name,
                    'gender' => $gender,
                    'email' => $email,
                    'password' => Hash::make($password)
                ]);
                echo json_encode([
                    'error' => false,
                    'message' => 'User Created!',
                    'data' => $res
                ]);

            } catch (\Illuminate\Database\QueryException $exception) {
                echo json_encode([
                    'error' => true,
                    'message' => 'Email sudah digunakan!'
                ]);
            }
        }
        else{
            echo json_encode([
                'error' => true,
                'message' => 'Data tidak lengkap!'
            ]);
        }
    }
}
