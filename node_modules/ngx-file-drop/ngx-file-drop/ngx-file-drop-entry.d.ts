import { FileSystemEntry } from './dom.types';
/**
 * fileEntry is an instance of {@link FileSystemFileEntry} or {@link FileSystemDirectoryEntry}.
 * Which one is it can be checked using {@link FileSystemEntry.isFile} or {@link FileSystemEntry.isDirectory}
 * properties of the given {@link FileSystemEntry}.
 */
export declare class NgxFileDropEntry {
    relativePath: string;
    fileEntry: FileSystemEntry;
    constructor(relativePath: string, fileEntry: FileSystemEntry);
}
