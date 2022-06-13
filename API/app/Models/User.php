<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    protected $fillable = ['email', 'password', 'name', 'gender'];
    protected $hidden = array('password');
}
