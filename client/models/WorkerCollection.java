package client.models;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import exceptions.IdentifierDoesNotExistError;

import java.io.*;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Set;

public class WorkerCollection implements Serializable{
    private Hashtable<String, Worker> workers = new Hashtable<>();
    private String loadedFrom;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    public String getLoadedFrom() {
        return loadedFrom;
    }

    public Hashtable<String, Worker> getWorkers() {
        return workers;
    }

    public boolean isEmpty() {
        return workers.isEmpty();
    }

    public Worker getWorker(String key) {
        return workers.get(key);
    }

    public Set<String> keySet() {
        return workers.keySet();
    }

    public void put(String key, Worker worker) {
        // todo: check for init
        workers.put(key, worker);
    }

    public Worker remove(String key) {
        return workers.remove(key);
    }

    public void clear() {
        workers.clear();
    }

    public int getSize() {
        return workers.size();
    }

    public LocalDateTime getLastSaveDate() {
        return lastSaveDate;
    }

    public void setCurrentDateAsLastSaveDate() {
        lastSaveDate = LocalDateTime.now();
    }

    public WorkerCollection filter(Filter filter) {
        WorkerCollection filteredWorkerCollection = new WorkerCollection();

        for (String key : workers.keySet()) {
            Worker worker = workers.get(key);
            if (filter.takeThis(worker)) {
                filteredWorkerCollection.put(key, worker);
            }
        }

        return filteredWorkerCollection;
    }

    public void dumpToJson() throws ParsingError {
        dumpToJson(loadedFrom);
    }

    public void dumpToJson(String filename) throws ParsingError {
        // todo: exception message
        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
            JSON.writeJSONString(outputStream, workers, SerializerFeature.PrettyFormat, SerializerFeature.IgnoreNonFieldGetter);
        } catch (IOException e) {
            throw new ParsingError();
        }
        setCurrentDateAsLastSaveDate();
    }

    protected Hashtable<String, Worker> parseJsonFile(String filename) throws ParsingError {
        // todo: exception
        try (FileInputStream file = new FileInputStream(filename)) {
            return JSON.parseObject(file, (new TypeReference<Hashtable<String, Worker>>() {}).getType());
        } catch (IOException | JSONException e ) {
            throw new ParsingError();
        }
    }

    public void loadFromJson(String filename) throws ParsingError {
        Hashtable<String, Worker> workers = parseJsonFile(filename);
        if (workers == null){
            workers = new Hashtable<String, Worker>();
        }
        for (Worker worker : workers.values()) {
            if (!worker.initialize()) {
                throw new ParsingError("Переданы поврежденные данные");
            }
        }
        this.workers = workers;
        loadedFrom = filename;
        setCurrentDateAsLastSaveDate();
    }

    public Worker getWorkerById(Integer id) {
        for (Worker worker : workers.values()) {
            if (worker.getId().equals(id)) {
                return worker;
            }
        }
        return null;
    }
}
