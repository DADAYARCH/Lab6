package client.models;

import exceptions.ValidationError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Worker implements Serializable {
    private static Integer lastSetID = 0;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int salary; //Значение поля должно быть больше 0
    private java.util.Date startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле не может быть null
    private Organization organization; //Поле не может быть null

    /**
     * Конструктор дает пустого рабочего
     */
    public Worker(){}

    /**
     * Конструктор для установки всех полей рабочего
     */
    public Worker(String name, Coordinates coordinates,  int salary, java.util.Date startDate, Position position, Status status, Organization organization){
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }



    /**
     * Проверяет значение полей на коректность,
     * и устанавливливает индификатор и дату создания, если она не установлена.
     *
     * @return прошла ли инициализация билет
     */
    public boolean initialize() {
        if (!isInitialized() && isValid()) {
            setCurrentDateAsCreationDate();
            generateAndSetID();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Проверяет, имеет ли рабочий идентификатор
     */
    public boolean isInitialized() {
        return id != null;
    }

    /**
     * Проверяет коректность заполненных данных.
     * @return коректны ли данные
     */
    public boolean isValid() {
        try {
            validateName();
            validateCoordinates();
            validateSalary();
            validateStartDate();
            validateStatus();
            validateOrganization();
            return true;
        } catch (ValidationError e) {
            return false;
        }
    }

    /**
     * Устанавливает текущую дату в качестве даты создания, если поле даты равно null
     */
    private void setCurrentDateAsCreationDate() {
        if (creationDate == null) {
            creationDate = java.time.LocalDateTime.now();
        }
    }

    /**
     * Проверяет есть ли свободные идентификаторы
     */
    private boolean isExistsUnusedIDs() {
        return lastSetID + 1 > 0;
    }

    /**
     * Генерирует идентификатор, если невозможно присвоить id то выбрасывает исключение IdentifierError
     *
     * @see IdentifierError
     */
    private void generateAndSetID() {
        if (!isInitialized() && isExistsUnusedIDs()) {
            id = ++lastSetID;
        } else {
            throw new IdentifierError();
        }
    }


    /**
     * Проверяет на валидность данные поля имя
     *
     * @throws ValidationError если поле не валидно
     */
    public void validateName() throws ValidationError {
        validateName(this.name);
    }

    /**
     * Проверяет на валидность строку для сохранения в поле имя.
     * @param name - проверяемая строка
     * @throws ValidationError если строку нельзя использовать в качестве имени.
     */
    public void validateName(String name) throws ValidationError {
        if (name == null || name.isEmpty()) {
            throw new ValidationError("Name must be not null or empty.");
        }
    }

    /**
     * Проверяет на валидность данные поля координат.
     *
     * @throws ValidationError если поле не валидно.
     */
    public void validateCoordinates() throws ValidationError {
        validateCoordinates(this.coordinates);
    }

    /**
     * Проверяет на валидность обьект Coordinates для сохранения в поле координат.
     *
     * @param coords - координаты для проверки
     * @see Coordinates
     * @throws ValidationError если переданные координаты пусты.
     */
    public void validateCoordinates(Coordinates coords) throws ValidationError {
        if (coords == null || !coords.isValid()) {
            throw new ValidationError("Coordinates must be not null.");
        }
    }

    public void validateSalary() throws ValidationError{
        validateSalary(this.salary);
    }

    public void validateSalary(int salary) throws ValidationError{
        if (salary <= 0){
            throw new ValidationError("The salary should be more than zero");
        }
    }

    public void validateStartDate() throws ValidationError{
        validateStartDate(this.startDate);
    }

    public void validateStartDate(java.util.Date startDate) throws ValidationError{
        if (startDate == null){
            throw new ValidationError("Start_Date can't be null");
        }
    }

    public void validateStatus() throws ValidationError{
        validateStatus(this.status);
    }

    public void validateStatus(Status status) throws ValidationError{
        if (status == null){
            throw new ValidationError("Status can't be null");
        }
    }

    public void validateOrganization() throws ValidationError{
        validateOrganization(this.organization);
    }

    public void validateOrganization(Organization organization) throws ValidationError{
        if (organization == null ){
            throw new ValidationError("Organization can't be null");
        }
    }

    public boolean isLowerThan(Worker other){
        return ((this.salary != 0 && other.salary != 0 && this.salary < other.salary) ||
                (this.name.length() < other.name.length()) ||
                this.coordinates.isLowerThan(other.coordinates) ||
                (this.organization != null && other.organization != null && this.organization.isLowerThan(other.organization))
        );
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public int getSalary(){
        return salary;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Organization getOrganization() {
        return organization;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

  /*  public int countDate(WorkerCollection workerCollection,String regex){
        int count = 0;
        if (workerCollection.getWorkers().contains(regex)){
            count =
        }
        return count;
    }*/


    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                "\n name='" + name + '\'' +
                "\n coordinates=" + coordinates +
                "\n creationDate=" + creationDate +
                "\n salary = " + salary+
                "\n startDate=" + startDate +
                "\n position=" +position+
                "\n status=" + status+
                "\n organization=" + organization+
                '}';
    }
}

