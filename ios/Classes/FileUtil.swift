//
//  FileUtil.swift
//  CAPP
//
//  Created by mac on 2020/1/28.
//  Copyright © 2020 capp. All rights reserved.
//

import Foundation
import UIKit


class FileUtil{
    
    //获取docpath
    class func getDocumentDir()->String{
        //方法1
        let documentPaths = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.documentDirectory,
                                                                FileManager.SearchPathDomainMask.userDomainMask, true)
        let documentPath:String = documentPaths[0]
        return documentPath
    }
    
    //写入txt文件
    class func writeFile(text:String, path:String){
//        let msg = "需要写入的资源"
//        let fileName = "c/学习笔记.txt"

        let fileManager = FileManager.default
//        let file = NSSearchPathForDirectoriesInDomains(FileManager.SearchPathDirectory.documentDirectory, FileManager.SearchPathDomainMask.userDomainMask, true).first
//        let path = file! + "/" + fileName

        fileManager.createFile(atPath: path, contents:nil, attributes:nil)
        print("写入文件:\(path) \(text)")

        let handle = FileHandle(forWritingAtPath:path)
        handle?.write(text.data(using: String.Encoding.utf8)!)
        do{
            if #available(iOS 13.0, *) {
                try handle?.close()
            } else {
                
            }
        }catch{
            print(error)
        }
        
    }
    
    //读取txt文件
       class func readFile(path:String) -> String?{
        var text:String? = nil
        do{
            text = try String(contentsOfFile: path,encoding: String.Encoding.utf8)
            return text
        }
        catch{
            print(error)
        }
        
        return nil
        }
    
    //创建一个文件夹
    class func createDir(name:String,baseUrl:URL) -> Void{
        let manager = FileManager.default
        let folder:URL = baseUrl.appendingPathComponent(name, isDirectory: true)
        print("文件夹: \(folder)")
        let exist = manager.fileExists(atPath: folder.path)
        if !exist {
            do{
//                try manager.createDirectory(at: folder, withIntermediateDirectories: true,attributes: nil)
                try manager.createDirectory(atPath: folder.path, withIntermediateDirectories: true, attributes: nil)
                
            }catch{
                print(error)
            }
            
        }
    }
    
    //获取用户文档路径
    class func getDocumentURL()->URL{
        let manager = FileManager.default
        let urlForDocument = manager.urls(for: .documentDirectory, in:.userDomainMask)
        let url = urlForDocument[0] as URL
        print(url)
        return url
    }
    
//    对指定路径执行浅搜索，返回指定目录路径下的文件、子目录及符号链接的列表
    class func getFileList(manager:FileManager, url:URL) -> [String]{
        do{
            let contentsOfPath:[String] = try manager.contentsOfDirectory(atPath: url.path)
            print("contentsOfPath: \(contentsOfPath)")
            return contentsOfPath
        }catch{
            print(error)
            return []
        }
       
        
    }
   
//    作者：_Waiting_
//    链接：https://www.jianshu.com/p/8feb8b0df1d0
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    
    
//    判断文件或文件夹是否存在
    class func exec(path:String) -> Bool{
        let fileManager = FileManager.default
//        let filePath:String = NSHomeDirectory() + "/Documents/hangge.txt"
        let exist = fileManager.fileExists(atPath: path)
return exist
    }
    //把图片保存到指定路径下
    class func saveImage(named image:String,path filePath:String)->Void{
//        let filePath = NSHomeDirectory() + "/Documents/hangge.png"
         let image = UIImage(named: image)
        let data:Data = image!.pngData()!
         try? data.write(to: URL(fileURLWithPath: filePath))
    }
 
    //删除文件
    class func deleteFile(path:String) ->Void{
        let fileManager = FileManager.default
//        let homeDirectory = NSHomeDirectory()
//        let srcUrl = homeDirectory + "/Documents/hangge.txt"
        try! fileManager.removeItem(atPath: path)
    }
    
//    文件权限操作
    class func isReadable(path:String) -> Bool{
        let manager = FileManager.default
//         let urlForDocument = manager.urls(for: .documentDirectory, in:.userDomainMask)
//         let docPath = urlForDocument[0]
//         let file = docPath.appendingPathComponent("test.txt")

        let readable = manager.isReadableFile(atPath: path)
        print("可读: \(readable)")
        return readable
    }
    
    class func isWriteable(path:String) -> Bool{
        let manager = FileManager.default
        let writeable = manager.isWritableFile(atPath: path)
        print("可写: \(writeable)")
        return writeable
    }
    
    class func isExecuteable(path:String) -> Bool{
        let manager = FileManager.default
        let executable = manager.isExecutableFile(atPath: path)
        print("可执行: \(executable)")
return executable
    }
    
    class func isDeleteable(path:String) -> Bool{
        let manager = FileManager.default
        let deleteable = manager.isDeletableFile(atPath: path)
        print("可删除: \(deleteable)")
        return deleteable
    }
     
}
