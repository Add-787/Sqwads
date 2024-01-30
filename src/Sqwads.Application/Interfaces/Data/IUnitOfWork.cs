namespace Sqwads.Application.Interfaces.Data;

public interface IUnitOfWork : IDisposable
{
    bool HasActiveTransaction { get; }
    Task BeginTransactionAsync();
    Task CommitTransactionAsync();
    Task RollbackTransactionAsync();
    public Task SaveChanges();

}
