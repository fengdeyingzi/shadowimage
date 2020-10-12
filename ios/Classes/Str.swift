//
//  Str.swift
//  ToolBox
//
//  Created by mac on 2020/5/30.
//  Copyright © 2020 message. All rights reserved.
//

import Foundation

class Str{
    public static func atoi(_ text:String)->Int{
        
        for index in text.indices {
//            print("\(text[index]) ", terminator: "")
            
        }
        if(text == nil){
            return 0;
        }
        var math:Int = 0;
        var isf:Bool=false;
                //跳过非数字
            /*
                while(text.charAt(ptr)>'9' || text.charAt(ptr)<'0')
                {
                    ptr++;
                }
        */
               for scala in text.unicodeScalars {
                    switch(scala)
                    {
                    case "0","1","2","3","4","5","6","7","8","9":
                        math=math*10+(Int(scala.value) - 48);
//                        print("str num \(scala.value)\n")
                            
                    case " ":
                        math = math + 0;
                            
                    case "-":
                            isf=true;
                            
                        default:
//                            print("math = \(math)\n")
                            return math;
                    }
                    
                }
        if isf {
            return -math;
        }
                    
                return math;
        // Prints "S t r e n g t h e n ! "
    }
}
