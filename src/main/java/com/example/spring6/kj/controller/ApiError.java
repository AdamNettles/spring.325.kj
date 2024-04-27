package com.example.spring6.kj.controller;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
record ApiError(String message){ }
